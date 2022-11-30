package fr.brilhante.joao.sprint.football.server.service;

import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.exception.TeamNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service to find football teams.
 */
public interface TeamFinder {

	/**
	 * Finds all the football teams (one page at a time).
	 *
	 * @param pageable the pagination and sorting options
	 * @return a page of football teams
	 */
	Page<TeamDto> getAllFootballTeams(Pageable pageable);

	/**
	 * Finds a football team by acronym.
	 *
	 * @param acronym the football team acronym to find
	 * @return the football team with the provided acronym, if found
	 * @throws TeamNotFoundException if no football team was found with the provided acronym
	 */
	TeamDto getFootballTeamByAcronym(String acronym);
}
