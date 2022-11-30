package fr.brilhante.joao.sprint.football.server.exception.handler;

import fr.brilhante.joao.sprint.football.server.exception.ResourceAlreadyExistsException;
import fr.brilhante.joao.sprint.football.server.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Exception handler of the application.
 * <p>
 * This exception handler only manages some exceptions because the framework already manages some of them.
 * </p>
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

	/**
	 * Handles a {@link ResourceNotFoundException}.
	 *
	 * @param exception the exception to handle
	 * @return the corresponding application error
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApplicationError handleResourceNotFoundException(ResourceNotFoundException exception) {
		return new ApplicationError(HttpStatus.NOT_FOUND.value(), "Resource Not Found", exception.getMessage());
	}

	/**
	 * Handles a {@link ResourceAlreadyExistsException}.
	 *
	 * @param exception the exception to handle
	 * @return the corresponding application error
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApplicationError handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
		return new ApplicationError(HttpStatus.CONFLICT.value(), "Resource Already Exists", exception.getMessage());
	}

	/**
	 * Handles a {@link MethodArgumentNotValidException}.
	 *
	 * @param exception the exception to handle
	 * @return the corresponding application error
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApplicationError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<String> errors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map((error -> error.getField() + " " + error.getDefaultMessage()))
			.toList();

		return new ApplicationError(
			HttpStatus.BAD_REQUEST.value(),
			"Invalid Argument",
			"The provided argument is invalid.",
			errors
		);
	}
}
