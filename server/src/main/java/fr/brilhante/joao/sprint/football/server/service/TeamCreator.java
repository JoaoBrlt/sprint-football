package fr.brilhante.joao.sprint.football.server.service;

import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.exception.TeamAcronymAlreadyUsedException;

/**
 * Service to create football teams.
 */
public interface TeamCreator {

	/**
	 * Creates a football team.
	 *
	 * @param teamDto the football team to create
	 * @throws TeamAcronymAlreadyUsedException if the provided acronym is already used by a football team
	 */
	void createFootballTeam(TeamDto teamDto);
}
