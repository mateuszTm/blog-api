package appbeta.blog.resource.server.dto;

import javax.validation.constraints.NotBlank;

public class EditProfileForm {
		
	@NotBlank
	private String description;
	
	public EditProfileForm() {}

	public EditProfileForm(String description) {
		this.description = description;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}
}
