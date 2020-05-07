package appbeta.blog.resource.server.dto;

import javax.validation.constraints.NotBlank;

public class AddPostForm {

	@NotBlank
	protected String title;
	
	@NotBlank
	protected String content;
	
	public AddPostForm() {}

	public AddPostForm(@NotBlank String title, @NotBlank String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
