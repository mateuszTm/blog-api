package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import appbeta.blog.service.UserService;
import appbeta.blog.dto.EditUserForm;
import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;
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
	
	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return userService.findUserById(id);
	}
	
	@GetMapping()
	public Page <User> getPage(Pageable pageable) {
		return userService.getAllUsers(pageable);
	}
	
	@PostMapping()
	public User add(@Valid @RequestBody User user, HttpServletRequest request) throws Exception {			
		for(Role r: user.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN") && !request.isUserInRole("ROLE_ADMIN")) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only ROLE_USER is allowed");
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
	public User editSelf(@Valid @RequestBody EditUserForm userForm, @PathVariable long id, Principal principal) {
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
	
	// TODO Autoryzacja użytkownika OAuth
}
