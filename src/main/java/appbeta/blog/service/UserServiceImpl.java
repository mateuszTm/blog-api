package appbeta.blog.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import appbeta.blog.dao.UserRepository;
import appbeta.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private EntityNotFoundException getUserNotFoundException(Long id) {
		return new EntityNotFoundException("User id " + id + " has not been found");
	}
	
	private EntityNotFoundException getUserNotFoundException(User user ) {
		return getUserNotFoundException(user.getId());
	}

	@Override
	@Transactional
	public void save(User user) {
		userRepository.save(user);		
	}
	
	@Override
	@Transactional
	public User updateUser(User user) {
		if (userRepository.existsById(user.getId())) {
			userRepository.save(user);
		} else {
			throw getUserNotFoundException(user);
		}
		return user;
	}

	@Override
	@Transactional
	public void remove(User user) {
		userRepository.delete(user);
	}
	
	@Override
	@Transactional
	public void removeById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User findUserByLogin(String login) {
		return userRepository.findByLogin(login).orElseThrow(() -> (new EntityNotFoundException("User with login '" + login + "' has not been found")));
	}
	
	@Override
	@Transactional
	public User findUserById(Long id){
		return userRepository.findById(id).orElseThrow(() -> getUserNotFoundException(id));
	}
}