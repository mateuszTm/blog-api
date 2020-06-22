package appbeta.blog.resource.server.dto;

import java.time.Instant;

import appbeta.blog.resource.server.entity.Post;

public class PostForm extends AddPostForm {

	protected Long id;

	protected Instant date;
	
	protected Long userId;
	
	protected String userName;
	
	public PostForm() {}
	
	public PostForm(Post post) {
		id = post.getId();
		date = post.getDate();
		title = post.getTitle();
		content = post.getContent();
		userId = post.getProfile().getId();
		userName = post.getProfile().getLogin();
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
