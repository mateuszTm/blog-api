package appbeta.blog.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import appbeta.blog.entity.Post;

public class AddPostForm extends PostForm{
	
	@NotNull
	protected Long userId;
	
	public AddPostForm () {}
	
	public AddPostForm (Post post) {
		date = post.getDate();
		title = post.getTitle();
		content = post.getContent();
		userId = post.getUser().getId();
	}

	public AddPostForm(Timestamp date, @NotBlank String title, @NotBlank String content,@NotNull Long userId) {
		this.date = date;
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
