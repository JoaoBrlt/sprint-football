package fr.brilhante.joao.sprint.football.server.dto;

import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

/**
 * Model of a football team used for data transfer.
 */
@Value
public class TeamDto {

	@NotNull
	@NotBlank
	String name;

	@NotNull
	@Pattern(regexp = "^[A-Z]{3}$", message = "must be composed of three capital letters")
	String acronym;

	@NotNull
	@PositiveOrZero
	BigDecimal budget;

	@Valid
	List<PlayerDto> players;
}
