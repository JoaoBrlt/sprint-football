package fr.brilhante.joao.sprint.football.server.exception;

/**
 * Signals that a resource was not found for given parameters.
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Constructs a {@link ResourceNotFoundException}.
	 *
	 * @param message the detail message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
