package fr.brilhante.joao.sprint.football.server.exception;

/**
 * Signals that a resource already exists for given parameters.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

	/**
	 * Constructs a {@link ResourceAlreadyExistsException}.
	 *
	 * @param message the detail message
	 */
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
