package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;

import appbeta.blog.service.RoleService;
import appbeta.blog.service.UserService;
import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	protected Set<Role> getUserRolesByName(User user) {
		return roleService.getRoleByName(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
	}
	
	@GetMapping()
	public List <User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.findUserById(id);
	}
	
	@PostMapping()
	public User add(@Valid @RequestBody User user) {
		user.setId(null);
		user.setRoles(getUserRolesByName(user));
		userService.save(user);
		return user;
	}
	
	
	@PutMapping()
	public User edit(@Valid @RequestBody User user) {
		user.setRoles(getUserRolesByName(user));
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.removeById(id);
	}
	
	// TODO Autoryzacja użytkownika OAuth
}
