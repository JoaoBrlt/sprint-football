package fr.brilhante.joao.sprint.football.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.brilhante.joao.sprint.football.server.dto.PlayerDto;
import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.exception.TeamAcronymAlreadyUsedException;
import fr.brilhante.joao.sprint.football.server.exception.TeamNotFoundException;
import fr.brilhante.joao.sprint.football.server.service.TeamCreator;
import fr.brilhante.joao.sprint.football.server.service.TeamFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TeamController.class)
class TeamControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TeamFinder teamFinder;

	@MockBean
	private TeamCreator teamCreator;

	@Test
	@DisplayName("Should get the football teams")
	void getTeams() throws Exception {
		PlayerDto playerDto = new PlayerDto("Lionel", "Messi", 10);
		TeamDto teamDto = new TeamDto("Paris Saint-Germain", "PSG", new BigDecimal("889550000"), List.of(playerDto));
		Page<TeamDto> page = new PageImpl<>(List.of(teamDto));

		when(teamFinder.getAllFootballTeams(any())).thenReturn(page);

		String expectedContent = objectMapper.writeValueAsString(page);
		mockMvc.perform(get("/teams")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedContent))
			.andDo(print());
	}

	@Test
	@DisplayName("Should create the football team")
	void createTeam() throws Exception {
		PlayerDto playerDto = new PlayerDto("Cristiano", "Ronaldo", 9);
		TeamDto teamDto = new TeamDto("Manchester United", "MAN", new BigDecimal("732600000"), List.of(playerDto));
		String content = objectMapper.writeValueAsString(teamDto);

		mockMvc.perform(post("/teams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isCreated())
			.andExpect(content().string(""))
			.andDo(print());
	}

	@Test
	@DisplayName("Should return an error when the football team is invalid")
	void createTeamWithInvalidRequestBody() throws Exception {
		PlayerDto playerDto = new PlayerDto("Rafa", "Silva", -1);
		TeamDto teamDto = new TeamDto("Benfica", "SLB", new BigDecimal("296000000"), List.of(playerDto));
		String content = objectMapper.writeValueAsString(teamDto);

		mockMvc.perform(post("/teams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.title").value("Invalid Argument"))
			.andExpect(jsonPath("$.errors").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("Should return an error when the football team already exists")
	void createTeamWithAlreadyUsedAcronym() throws Exception {
		PlayerDto playerDto = new PlayerDto("Antoine", "Griezmann", 7);
		TeamDto teamDto = new TeamDto("Atlético de Madrid", "AMD", new BigDecimal("520500000"), List.of(playerDto));
		String content = objectMapper.writeValueAsString(teamDto);

		doThrow(new TeamAcronymAlreadyUsedException("AMD")).when(teamCreator).createFootballTeam(teamDto);

		mockMvc.perform(post("/teams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isConflict())
			.andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
			.andExpect(jsonPath("$.title").value("Resource Already Exists"))
			.andDo(print());
	}

	@Test
	@DisplayName("Should get a football team by acronym")
	void getTeam() throws Exception {
		PlayerDto playerDto = new PlayerDto("Matt", "Turner", 1);
		TeamDto teamDto = new TeamDto("Arsenal", "ARS", new BigDecimal("749000000"), List.of(playerDto));

		when(teamFinder.getFootballTeamByAcronym("ARS")).thenReturn(teamDto);

		String expectedContent = objectMapper.writeValueAsString(teamDto);
		mockMvc.perform(get("/teams/ARS")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedContent))
			.andDo(print());
	}

	@Test
	@DisplayName("Should return an error when no football team is found with the provided acronym")
	void getTeamWithTeamNotFound() throws Exception {
		doThrow(new TeamNotFoundException("ARS")).when(teamFinder).getFootballTeamByAcronym("ARS");

		mockMvc.perform(get("/teams/ARS")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
			.andExpect(jsonPath("$.title").value("Resource Not Found"))
			.andDo(print());
	}
}
