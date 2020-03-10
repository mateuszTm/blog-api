package appbeta.blog.dto;

import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import appbeta.blog.entity.Post;

@JsonInclude(Include.NON_NULL)
public class PostForm {
	
	protected Long id;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Timestamp date;
	
	@NotBlank
	protected String title;
	
	@NotBlank
	protected String content;
	
	protected Long userId;
	
	protected String userName;
	
	public PostForm () {}
	
	public PostForm (Post post) {
		id = post.getId();
		date = post.getDate();
		title = post.getTitle();
		content = post.getContent();
		userId = post.getUser().getId();
		userName = post.getUser().getLogin();
	}

	public PostForm(Long id, Timestamp date, @NotBlank String title, @NotBlank String content, Long userId,
			String userName) {
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
