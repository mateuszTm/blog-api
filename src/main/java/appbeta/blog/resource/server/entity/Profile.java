package appbeta.blog.resource.server.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Entity
@Table(name="profile")
public class Profile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotNull
	private String login;
	
	@Lob
	private String description;
	
	private boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy="profile", fetch=FetchType.LAZY)
	private List<Post> posts;
	
	public Profile() {}
	
	public Profile(String login, String description, boolean active) {
		this.login = login;
		this.description = description;
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean locked) {
		this.active = locked;
	}
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public Profile addPost(Post post) {
		this.posts.add(post);
		post.setProfile(this);
		return this;
	}	
}
