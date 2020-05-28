package appbeta.blog.resource.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditProfileForm {
		
	@NotBlank
	private String description;
	
	@NotNull
	private boolean active;
	
	public EditProfileForm() {}

	public EditProfileForm(String description, boolean active) {
		this.description = description;
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
