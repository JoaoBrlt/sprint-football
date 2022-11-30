package fr.brilhante.joao.sprint.football.server.controller;

import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.service.TeamCreator;
import fr.brilhante.joao.sprint.football.server.service.TeamFinder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that provides endpoints to manage football teams.
 */
@RestController
@RequestMapping("teams")
@RequiredArgsConstructor
public class TeamController {

	private final TeamCreator teamCreator;
	private final TeamFinder teamFinder;

	@GetMapping
	@Operation(summary = "Get all football teams")
	@ApiResponse(responseCode = "200", description = "The request was successfully completed")
	public Page<TeamDto> getTeams(@ParameterObject Pageable pageable) {
		return teamFinder.getAllFootballTeams(pageable);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a football team")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "The football team was successfully created"),
		@ApiResponse(responseCode = "400", description = "The football team is invalid"),
		@ApiResponse(responseCode = "409", description = "The football team already exists")
	})
	public void createTeam(@Valid @RequestBody TeamDto teamDto) {
		teamCreator.createFootballTeam(teamDto);
	}

	@GetMapping("{acronym}")
	@Operation(summary = "Get a football team by acronym")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "The football team was found"),
		@ApiResponse(responseCode = "404", description = "The football team was not found"),
	})
	public TeamDto getTeam(@PathVariable String acronym) {
		return teamFinder.getFootballTeamByAcronym(acronym);
	}
}
