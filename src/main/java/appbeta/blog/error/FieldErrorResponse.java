package appbeta.blog.error;

import org.springframework.validation.FieldError;

public class FieldErrorResponse extends AbstractErrorResponse {
	
	private String objectName;
	private String fieldName;
	private String rejectedValue;
	
	public FieldErrorResponse(String message, String objectName, String fieldName, String rejectedValue) {
		this.message = message;
		this.objectName = objectName;
		this.fieldName = fieldName;
		this.rejectedValue = rejectedValue;
	}
	
	public FieldErrorResponse(FieldError error) {
		this.message = error.getDefaultMessage();
		this.objectName = error.getObjectName();
		this.fieldName = error.getField();
		this.rejectedValue = String.valueOf(error.getRejectedValue());
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
}