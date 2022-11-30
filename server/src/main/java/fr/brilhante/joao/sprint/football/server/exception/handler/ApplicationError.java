package fr.brilhante.joao.sprint.football.server.exception.handler;

import lombok.Value;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * Model of an application error.
 */
@Value
public class ApplicationError {

	int status;

	String title;

	String message;

	List<String> errors;

	Instant date = Instant.now();

	/**
	 * Constructs an application error with error details.
	 *
	 * @param status  the error status
	 * @param title   the error title
	 * @param message the detail message
	 * @param errors  the list of errors that caused the problem
	 */
	public ApplicationError(int status, String title, String message, List<String> errors) {
		this.status = status;
		this.title = title;
		this.message = message;
		this.errors = errors;
	}

	/**
	 * Constructs an application error.
	 *
	 * @param status  the error status
	 * @param title   the error title
	 * @param message the detail message
	 */
	public ApplicationError(int status, String title, String message) {
		this(status, title, message, Collections.emptyList());
	}
}
