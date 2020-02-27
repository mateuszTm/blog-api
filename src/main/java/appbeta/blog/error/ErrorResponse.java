package appbeta.blog.error;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.LocalDateTime;
import java.util.List;


public class ErrorResponse {
	
	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private String message;

	@JsonInclude(Include.NON_NULL)
	private List<AbstractErrorResponse> errors;
	
	public ErrorResponse () {
		timestamp = LocalDateTime.now();
	}
	
	public ErrorResponse (HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public ErrorResponse (HttpStatus status, Throwable exception) {
		this();
		this.status = status;
		this.message = exception.getMessage();
	}
	
	public ErrorResponse (HttpStatus status, String message, Throwable exception) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public List<AbstractErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<AbstractErrorResponse> errors) {
		this.errors = errors;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
