package appbeta.blog.service;

import java.util.List;
import appbeta.blog.entity.User;

public interface UserService {

	public void save(User user);
	
	public void remove (User user);
	
	public List <User> getAllUsers ();
	
	public User findUserByLogin(String  login);
}
