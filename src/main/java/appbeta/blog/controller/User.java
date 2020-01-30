package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/user")
public class User {

	// TODO
	@GetMapping("/add")
	public String add() {
		return "todo user.add";
	}
	
	// TODO
	public String edit() {
		return "todo user.edit";
	}
	
	// TODO
	public String delete() {
		return "todo user.delete";
	}
	
	// TODO
	@GetMapping("/info")
	public String getInfo() {
		return "todo user.getInfo";
	}
	
	// TODO Autoryzacja użytkownika OAuth
}
