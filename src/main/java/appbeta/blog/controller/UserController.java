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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public List <User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.findUserById(id).orElseThrow(() -> new RuntimeException("User id " + id + " has not been found"));
	}

	@PostMapping()
	public User add(@RequestBody User user) {
		user.setId(null);
		userService.save(user);
		return user;
	}
	
	
	@PutMapping()
	public User edit(@RequestBody User user) {
		if (userService.findUserById(user.getId()).isPresent()) {
			userService.save(user);
		} else {
			throw new RuntimeException("User id " + user.getId() + " does not exist");
		}
		return user;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.removeById(id);
	}
	
	// TODO Autoryzacja użytkownika OAuth
}
