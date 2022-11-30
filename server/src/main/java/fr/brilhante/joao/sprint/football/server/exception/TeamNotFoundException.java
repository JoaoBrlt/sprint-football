package fr.brilhante.joao.sprint.football.server.exception;

/**
 * Signals that a football team was not found.
 */
public class TeamNotFoundException extends ResourceNotFoundException {

	/**
	 * Constructs a {@link TeamAcronymAlreadyUsedException}.
	 *
	 * @param acronym the acronym not found
	 */
	public TeamNotFoundException(String acronym) {
		super(String.format("No football team was found with the acronym '%s'.", acronym));
	}
}
