package fr.brilhante.joao.sprint.football.server.dto;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Model of a football player used for data transfer.
 */
@Value
public class PlayerDto {

	@NotNull
	@NotBlank
	String firstName;

	@NotNull
	@NotBlank
	String lastName;

	@NotNull
	@Min(1)
	@Max(11)
	Integer position;
}
