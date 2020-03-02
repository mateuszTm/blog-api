package appbeta.blog.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import appbeta.blog.entity.Role;

public interface RoleService {
	
	public void save (Role role);
	
	public void remove (Role role);
	
	public List <Role> getAllRoles ();
	
	public Role getRoleByName(String name);
	
	public Set<Role> getRoleByName(Collection<String> name);
}
