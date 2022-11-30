package fr.brilhante.joao.sprint.football.server.mapper;

import fr.brilhante.joao.sprint.football.server.dto.TeamDto;
import fr.brilhante.joao.sprint.football.server.model.Team;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper to convert football team entities and DTOs.
 */
@Mapper(uses = PlayerMapper.class)
public interface TeamMapper {

	/**
	 * Converts a football team DTO to an entity.
	 *
	 * @param teamDto the football team DTO to convert
	 * @return the newly created football team entity
	 */
	Team toEntity(TeamDto teamDto);

	/**
	 * Converts a football team entity to a DTO.
	 *
	 * @param team the football team entity to convert
	 * @return the newly created football team DTO
	 */
	TeamDto toDto(Team team);

	/**
	 * Defines the football team of each player so that the association is properly registered in the database.
	 *
	 * @param team the newly created football team entity
	 */
	@AfterMapping
	default void updatePlayers(@MappingTarget Team team) {
		team.getPlayers().forEach(player -> player.setTeam(team));
	}
}
