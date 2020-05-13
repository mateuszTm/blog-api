package appbeta.blog.resource.server.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import appbeta.blog.resource.server.dto.EditUserForm;
import appbeta.blog.resource.server.dto.PostForm;
import appbeta.blog.resource.server.entity.Post;
import appbeta.blog.resource.server.entity.Role;
import appbeta.blog.resource.server.entity.User;
import appbeta.blog.resource.server.service.PostService;
import appbeta.blog.resource.server.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return userService.findUserById(id);
	}
	
	@GetMapping()
	public User getSelf(Principal principal) {
		return userService.findUserByLogin(principal.getName());
	}
	
	@GetMapping("/list")
	public Page <User> getPage(Pageable pageable) {
		return userService.getAllUsers(pageable);
	}
	
	@PostMapping()
	public User add(@Valid @RequestBody User user, HttpServletRequest request) throws Exception {
		String requiredRole = appbeta.blog.resource.server.config.Role.ADMIN.authority;
		for(Role r: user.getRoles()) {
			if (r.getName().equals(requiredRole) && !request.isUserInRole(requiredRole)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to add user with role " + r.getName());
			}
		}		
		userService.add(user);
		return user;
	}
	
	
	@PutMapping("/{id}")
	public User edit(@Valid @RequestBody User user, @PathVariable long id) {
		user.setId(id);
		userService.updateUser(user);
		return user;
	}
	
	@PutMapping
	public User editSelf(@Valid @RequestBody EditUserForm userForm, Principal principal) {
		User user = userService.findUserByLogin(principal.getName());
		user.setLogin(userForm.getLogin());
		user.setPassword(userForm.getPassword());
		userService.updateUser(user);
		return user;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.removeById(id);
	}
	
	@DeleteMapping
	public void deleteSelf(Principal principal) {
		User user = userService.findUserByLogin(principal.getName());
		userService.remove(user);
	}
	
	@GetMapping("/post")
	public Page<PostForm> getUserPosts(Pageable pageable, Principal principal){
		return postService.getByUser(
				pageable,
				userService.findUserByLogin(principal.getName())
			).map((Post post) -> new PostForm(post));
	}
}
