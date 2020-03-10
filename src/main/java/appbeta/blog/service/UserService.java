package appbeta.blog.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import appbeta.blog.entity.User;

public interface UserService {

	public void add (User user);
	
	public void updateUser (User user);
	
	public void remove (User user);
	
	public void removeById (Long id);
	
	public Page <User> getAllUsers (Pageable pageable);
	
	public User findUserByLogin (String  login);
	
	public User findUserById (Long id);
}
