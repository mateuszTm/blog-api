package appbeta.blog.service;

import java.util.List;

import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;

public interface UserService {

	public void save(User user);
	
	public void remove (User user);
	
	public List <User> getAllUsers ();
	
	public User findUserByLogin();
	
//	public List <Role> getUserRoles(User user);
	
	public void addUserRole(User user, List <Role> role);
	
	public void addUserRole(User user, Role role);
	
	public void removeUserRole(User user, Role role);
}
