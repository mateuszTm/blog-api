package appbeta.blog.error;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private void logError(Throwable ex) {
		log.error("User: xxxx\n", ex);
	}
	
	private ResponseEntity<Object> getResponseEntity (HttpStatus status, Throwable ex, HttpServletRequest request) {
		log.error("User: xxxx\nMethod: {}\nUri: {}", request.getMethod(), request.getRequestURI(), ex);
		return new ResponseEntity<>(new ErrorResponse(status, ex), status);
	}
	
	private ResponseEntity<Object> getResponseEntity (ErrorResponse response, Throwable ex, HttpServletRequest request) {
		log.error("User: xxxx\nMethod: {}\nUri: {}", request.getMethod(), request.getRequestURI(), ex);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
		logError(ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, ex);
		response.setErrors(ex.getBindingResult().getFieldErrors().stream().map(er -> new FieldErrorResponse(er)).collect(Collectors.toList()));
		response.setMessage("Invalid field value");
		return getResponseEntity(response, ex, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity <Object> handleResourceNotFound (EntityNotFoundException exception, HttpServletRequest request) {
		return getResponseEntity(HttpStatus.NOT_FOUND, exception, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity <Object> handleEmptyResultData(EmptyResultDataAccessException ex, HttpServletRequest request) {
		return getResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND, "Resource not found"), ex, request);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity <Object> responseStatusExceptiontHandler (ResponseStatusException exception, HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(exception.getStatus());
		response.setMessage(exception.getReason());
		return getResponseEntity(response, exception, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity <Object> defaultHandler (Exception exception, HttpServletRequest request) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
	}
}
