package appbeta.blog.resource.server.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import appbeta.blog.resource.server.entity.Post;

public class PostForm extends AddPostForm {

	protected Long id;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	protected Instant date;
	
	protected Long userId;
	
	protected String userName;
	
	public PostForm() {}
	
	public PostForm(Post post) {
		id = post.getId();
		date = post.getDate();
		title = post.getTitle();
		content = post.getContent();
		userId = post.getUser().getId();
		userName = post.getUser().getLogin();
	}
	
	public PostForm(Long id, Instant date, String title, String content, Long userId, String userName) {
		this.id = id;
		this.date = date;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
