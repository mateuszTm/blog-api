package appbeta.blog.resource.server.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import appbeta.blog.resource.server.entity.Role;

public interface RoleService {
	
	public void add (Role role);
	
	public void update (Role role);
	
	public void remove (Role role);
	
	public void removeById (Long id);
	
	public Page <Role> getAll (Pageable pageable);
	
	public Role getRoleByName (String name);
	
	public Set<Role> getRoleByName (Collection<String> name);
	
	public Role getRoleByid (long id);
}
