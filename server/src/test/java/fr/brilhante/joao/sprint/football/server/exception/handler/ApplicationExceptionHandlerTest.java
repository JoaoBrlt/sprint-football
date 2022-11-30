package fr.brilhante.joao.sprint.football.server.exception.handler;

import fr.brilhante.joao.sprint.football.server.exception.ResourceAlreadyExistsException;
import fr.brilhante.joao.sprint.football.server.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;
import java.util.Spliterators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationExceptionHandlerTest {

	private ApplicationExceptionHandler exceptionHandler;

	@BeforeEach
	void setUp() {
		exceptionHandler = new ApplicationExceptionHandler();
	}

	@Test
	@DisplayName("Should handle a resource not found exception")
	void handleResourceNotFoundException() {
		ResourceNotFoundException exception = new ResourceNotFoundException("Detailed message");

		ApplicationError error = exceptionHandler.handleResourceNotFoundException(exception);

		assertThat(error.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(error.getTitle()).isEqualTo("Resource Not Found");
		assertThat(error.getMessage()).isEqualTo("Detailed message");
		assertThat(error.getErrors()).isEmpty();
		assertThat(error.getDate()).isNotNull();
	}

	@Test
	@DisplayName("Should handle a resource already exists exception")
	void handleResourceAlreadyExistsException() {
		ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException("Detailed message");

		ApplicationError error = exceptionHandler.handleResourceAlreadyExistsException(exception);

		assertThat(error.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
		assertThat(error.getTitle()).isEqualTo("Resource Already Exists");
		assertThat(error.getMessage()).isEqualTo("Detailed message");
		assertThat(error.getErrors()).isEmpty();
		assertThat(error.getDate()).isNotNull();
	}

	@Test
	@DisplayName("Should handle a method argument not valid exception")
	void handleMethodArgumentNotValidException() {
		MethodParameter methodParameter = mock(MethodParameter.class);
		BindingResult bindingResult = mock(BindingResult.class);
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

		when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("object", "field", "must not be null")));

		ApplicationError error = exceptionHandler.handleMethodArgumentNotValidException(exception);

		assertThat(error.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(error.getTitle()).isEqualTo("Invalid Argument");
		assertThat(error.getMessage()).isEqualTo("The provided argument is invalid.");
		assertThat(error.getErrors()).hasSize(1);
		assertThat(error.getErrors().get(0)).isEqualTo("field must not be null");
		assertThat(error.getDate()).isNotNull();
	}
}
