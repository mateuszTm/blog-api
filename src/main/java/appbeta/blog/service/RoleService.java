package appbeta.blog.service;

import java.util.List;

import appbeta.blog.entity.Role;

public interface RoleService {
	
	public void save (Role role);
	
	public void remove (Role role);
	
	public List <Role> getAllRoles ();
}
