package fr.brilhante.joao.sprint.football.server.service.implementation;

import fr.brilhante.joao.sprint.football.server.dto.PlayerDto;
import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.exception.TeamAcronymAlreadyUsedException;
import fr.brilhante.joao.sprint.football.server.exception.TeamNotFoundException;
import fr.brilhante.joao.sprint.football.server.mapper.TeamMapper;
import fr.brilhante.joao.sprint.football.server.model.Player;
import fr.brilhante.joao.sprint.football.server.model.Team;
import fr.brilhante.joao.sprint.football.server.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private TeamMapper teamMapper;

	@InjectMocks
	private TeamService teamService;

	@Test
	@DisplayName("Should save the football team")
	void createFootballTeam() {
		PlayerDto playerDto = new PlayerDto("Ousmane", "Dembélé", 7);
		TeamDto teamDto = new TeamDto("FC Barcelone", "BAR", new BigDecimal("799500000"), List.of(playerDto));

		Player player = new Player("Ousmane", "Dembélé", 7);
		Team team = new Team("FC Barcelone", "BAR", new BigDecimal("799500000"));
		team.addPlayer(player);

		when(teamRepository.findByAcronymIgnoreCase("BAR")).thenReturn(Optional.empty());
		when(teamMapper.toEntity(teamDto)).thenReturn(team);

		teamService.createFootballTeam(teamDto);

		verify(teamRepository).save(team);
	}

	@Test
	@DisplayName("Should throw an exception when the acronym is already used")
	void createFootballTeamWithAlreadyUsedAcronym() {
		PlayerDto playerDto = new PlayerDto("Karim", "Benzema", 9);
		TeamDto teamDto = new TeamDto("Real Madrid", "MAD", new BigDecimal("839000000"), List.of(playerDto));

		Team otherTeam = new Team("Atlético de Madrid", "MAD", new BigDecimal("520500000"));
		when(teamRepository.findByAcronymIgnoreCase("MAD")).thenReturn(Optional.of(otherTeam));

		assertThrows(TeamAcronymAlreadyUsedException.class, () -> teamService.createFootballTeam(teamDto));
	}

	@Test
	@DisplayName("Should get the football teams")
	void getAllFootballTeams() {
		Player player = new Player("Calvin", "Ramsay", 2);
		Team team = new Team("Liverpool", "LIV", new BigDecimal("867000000"));
		team.addPlayer(player);

		Page<Team> page = new PageImpl<>(List.of(team));
		Pageable pageable = PageRequest.of(0, 20);

		PlayerDto playerDto = new PlayerDto("Calvin", "Ramsay", 2);
		TeamDto teamDto = new TeamDto("Liverpool", "LIV", new BigDecimal("867000000"), List.of(playerDto));

		when(teamRepository.findAll(pageable)).thenReturn(page);
		when(teamMapper.toDto(team)).thenReturn(teamDto);

		Page<TeamDto> result = teamService.getAllFootballTeams(pageable);
		assertThat(result.getContent()).hasSize(1);
		assertThat(result.getContent().get(0)).isEqualTo(teamDto);
	}

	@Test
	@DisplayName("Should get a football team by acronym")
	void getFootballTeamByAcronym() {
		Player player = new Player("Bernardo", "Silva", 8);
		Team team = new Team("Manchester City", "MNC", new BigDecimal("1090000000"));
		team.addPlayer(player);

		PlayerDto playerDto = new PlayerDto("Bernardo", "Silva", 8);
		TeamDto teamDto = new TeamDto("Manchester City", "MNC", new BigDecimal("1090000000"), List.of(playerDto));

		when(teamRepository.findByAcronymIgnoreCase("MNC")).thenReturn(Optional.of(team));
		when(teamMapper.toDto(team)).thenReturn(teamDto);

		TeamDto actualTeamDto = teamService.getFootballTeamByAcronym("MNC");
		assertThat(actualTeamDto).isEqualTo(teamDto);
	}

	@Test
	@DisplayName("Should throw an exception when no football team is found with the provided acronym")
	void getFootballTeamByAcronymWithTeamNotFound() {
		when(teamRepository.findByAcronymIgnoreCase("ARS")).thenReturn(Optional.empty());

		assertThrows(TeamNotFoundException.class, () -> teamService.getFootballTeamByAcronym("ARS"));
	}
}
