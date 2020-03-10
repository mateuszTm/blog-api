package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;

import appbeta.blog.service.UserService;
import appbeta.blog.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@GetMapping()
	public Page <User> getPage(Pageable pageable) {
		return userService.getAllUsers(pageable);
	}
	
	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return userService.findUserById(id);
	}
	
	@PostMapping()
	public User add(@Valid @RequestBody User user) {
		userService.add(user);
		return user;
	}
	
	
	@PutMapping("/{id}")
	public User edit(@Valid @RequestBody User user, @PathVariable long id) {
		user.setId(id);
		userService.updateUser(user);
		return user;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.removeById(id);
	}
	
	// TODO Autoryzacja użytkownika OAuth
}
