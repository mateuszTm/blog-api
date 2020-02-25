package appbeta.blog.service;

import java.util.List;
import java.util.Optional;

import appbeta.blog.entity.User;

public interface UserService {

	public void save(User user);
	
	public User updateUser(User user);
	
	public void remove (User user);
	
	public void removeById(Long id);
	
	public List <User> getAllUsers ();
	
	public User findUserByLogin(String  login);
	
	public User findUserById(Long id);
}
