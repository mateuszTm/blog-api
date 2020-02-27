package appbeta.blog.error;

public abstract class AbstractErrorResponse {
	
	protected String message;
	
	public AbstractErrorResponse() {}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
