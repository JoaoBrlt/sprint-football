package fr.brilhante.joao.sprint.football.server.exception;

/**
 * Signals that an acronym is already used by a football team.
 */
public class TeamAcronymAlreadyUsedException extends ResourceAlreadyExistsException {

	/**
	 * Constructs a {@link TeamAcronymAlreadyUsedException}.
	 *
	 * @param acronym the conflicting acronym
	 */
	public TeamAcronymAlreadyUsedException(String acronym) {
		super(String.format("A football team already uses the acronym '%s'.", acronym));
	}
}
