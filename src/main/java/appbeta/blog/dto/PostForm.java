package appbeta.blog.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import appbeta.blog.entity.Post;

public class PostForm extends AddPostForm {

	protected Long id;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Timestamp date;
	
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
	
	public PostForm(Long id, Timestamp date, String title, String content, Long userId, String userName) {
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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
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
