package appbeta.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.Set;
import javax.persistence.FetchType;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String login;
	
	private String password;
	
	private boolean locked;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Post> posts;
	
	// TODO przemyśleć czy powinno być fetchType.EAGER?
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="user_role",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="role_id")
			)
	private Set<Role> roles;

	public User() {}
	
	public User(String login, String password, boolean locked) {
		this.login = login;
		this.password = password;
		this.locked = locked;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public User addRole(Role role) {
		this.roles.add(role);
		role.getUsers().add(this);
		return this;
	}
	
	public void removeRole(Role role) {
		this.roles.remove(role);
		role.getUsers().remove(this);
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public User addPost(Post post) {
		this.posts.add(post);
		post.setUser(this);
		return this;
	}	
}
