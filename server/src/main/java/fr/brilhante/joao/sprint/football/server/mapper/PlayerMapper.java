package fr.brilhante.joao.sprint.football.server.mapper;

import fr.brilhante.joao.sprint.football.server.dto.PlayerDto;
import fr.brilhante.joao.sprint.football.server.model.Player;
import org.mapstruct.Mapper;

/**
 * Mapper to convert football player entities and DTOs.
 */
@Mapper
public interface PlayerMapper {

	/**
	 * Converts a football player DTO to an entity.
	 *
	 * @param playerDto the football player DTO to convert
	 * @return the newly created football player entity
	 */
	Player toEntity(PlayerDto playerDto);

	/**
	 * Converts a football player entity to a DTO.
	 *
	 * @param player the football player entity to convert
	 * @return the newly created football team DTO
	 */
	PlayerDto toDto(Player player);
}
