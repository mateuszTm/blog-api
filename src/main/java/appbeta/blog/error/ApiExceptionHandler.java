package appbeta.blog.error;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Iterator;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private void logError(Throwable ex, WebRequest request) {		
		log.error(request.getDescription(true), ex);
	}
	
	private ResponseEntity<Object> getResponseEntity (ErrorResponse response) {
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	private ResponseEntity<Object> getResponseEntity (HttpStatus status, Throwable ex) {
		return new ResponseEntity<>(new ErrorResponse(status, ex), status);
	}
	
	private ResponseEntity<Object> getResponseEntity (HttpStatus status, String message) {
		return new ResponseEntity<>(new ErrorResponse(status, message), status);
	}
		
	private ResponseEntity<Object> returnExceptionResponse(HttpStatus status, Throwable ex, WebRequest request) {
		logError(ex, request);
		return getResponseEntity(status, ex);
	}
	
	private ResponseEntity<Object> returnExceptionResponse(HttpStatus status, String message, Throwable ex, WebRequest request) {
		logError(ex, request);
		return getResponseEntity(status, message);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logError(ex, request);
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid field value");
		response.setErrors(ex.getBindingResult().getFieldErrors().stream().map(er -> new FieldErrorResponse(er)).collect(Collectors.toList()));
		return getResponseEntity(response);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity <Object> handleResourceNotFound (EntityNotFoundException exception, WebRequest request) {
		return returnExceptionResponse(HttpStatus.NOT_FOUND, exception, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity <Object> handleEmptyResultData(EmptyResultDataAccessException ex, WebRequest request) {
		return returnExceptionResponse(HttpStatus.NOT_FOUND, "Resource not found", ex, request);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity <Object> responseStatusExceptiontHandler (ResponseStatusException exception, WebRequest request) {
		return returnExceptionResponse(exception.getStatus(), exception.getReason(), exception, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity <Object> dataIntegrityViolationExceptionHandler (DataIntegrityViolationException exception, WebRequest request) {
		return returnExceptionResponse(HttpStatus.BAD_REQUEST, exception.getRootCause(), request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity <Object> defaultHandler (Exception exception, WebRequest request) {
		return returnExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
	}
}
