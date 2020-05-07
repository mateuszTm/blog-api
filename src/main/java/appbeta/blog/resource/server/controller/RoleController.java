package appbeta.blog.resource.server.controller;

import appbeta.blog.resource.server.entity.Role;
import appbeta.blog.resource.server.service.RoleService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@PostMapping
	public Role add(@Valid @RequestBody Role role) {
		roleService.add(role);
		return role;
	}
	
	@PutMapping("/{id}")
	public Role edit(@Valid @RequestBody Role role, @PathVariable long id) {
		role.setId(id);
		roleService.update(role);
		return role;
	}
	
	@GetMapping("/{id}")
	public Role get(@PathVariable Long id) {
		return roleService.getRoleByid(id);
	}
	
	@GetMapping
	public Page<Role> getPage(Pageable pageable) {
		return roleService.getAll(pageable);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		roleService.removeById(id);
	}
}
