package appbeta.blog.resource.server.dto;

import javax.validation.constraints.NotBlank;

public class EditUserForm {
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String password;
	
	public EditUserForm() {}

	public EditUserForm(@NotBlank String login, @NotBlank String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
