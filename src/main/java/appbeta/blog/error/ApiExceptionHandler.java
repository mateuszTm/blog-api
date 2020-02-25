package appbeta.blog.error;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private ResponseEntity<Object> getResponseEntity (HttpStatus status, Throwable ex, HttpServletRequest request) {
		log.error("User: xxxx\nMethod: {}\nUri: {}", request.getMethod(), request.getRequestURI(), ex);
		return new ResponseEntity<>(new ErrorResponse(status, ex), status);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity <Object> handleResourceNotFound (EntityNotFoundException exception, HttpServletRequest request) {
		return getResponseEntity(HttpStatus.NOT_FOUND, exception, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity <Object> handleEmptyResultData(EmptyResultDataAccessException ex, HttpServletRequest request) {
		return getResponseEntity(HttpStatus.NOT_FOUND, ex, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity <Object> defaultHandler (Exception exception, HttpServletRequest request) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
	}
}
