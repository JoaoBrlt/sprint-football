package fr.brilhante.joao.sprint.football.server.service.implementation;

import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.exception.TeamAcronymAlreadyUsedException;
import fr.brilhante.joao.sprint.football.server.exception.TeamNotFoundException;
import fr.brilhante.joao.sprint.football.server.mapper.TeamMapper;
import fr.brilhante.joao.sprint.football.server.model.Team;
import fr.brilhante.joao.sprint.football.server.repository.TeamRepository;
import fr.brilhante.joao.sprint.football.server.service.TeamCreator;
import fr.brilhante.joao.sprint.football.server.service.TeamFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service implementation to create and find football teams.
 */
@Service
@RequiredArgsConstructor
public class TeamService implements TeamCreator, TeamFinder {

	private final TeamRepository teamRepository;
	private final TeamMapper teamMapper;

	private boolean isAcronymAlreadyUsed(String acronym) {
		return teamRepository.findByAcronymIgnoreCase(acronym).isPresent();
	}

	@Override
	public void createFootballTeam(TeamDto teamDto) {
		if (isAcronymAlreadyUsed(teamDto.getAcronym())) {
			throw new TeamAcronymAlreadyUsedException(teamDto.getAcronym());
		}
		Team team = teamMapper.toEntity(teamDto);
		teamRepository.save(team);
	}

	@Override
	public Page<TeamDto> getAllFootballTeams(Pageable pageable) {
		return teamRepository
			.findAll(pageable)
			.map(teamMapper::toDto);
	}

	@Override
	public TeamDto getFootballTeamByAcronym(String acronym) {
		return teamRepository
			.findByAcronymIgnoreCase(acronym)
			.map(teamMapper::toDto)
			.orElseThrow(() -> new TeamNotFoundException(acronym));
	}
}
