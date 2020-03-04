package appbeta.blog.error;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


public class ErrorResponse {
	
	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp timestamp;
	
	private String message;

	@JsonInclude(Include.NON_NULL)
	private List<AbstractErrorResponse> errors;
	
	public ErrorResponse () {
		timestamp = new Timestamp(new Date().getTime());
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
