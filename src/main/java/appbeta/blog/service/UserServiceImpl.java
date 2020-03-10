package appbeta.blog.service;


import javax.persistence.EntityNotFoundException;

import appbeta.blog.dao.UserRepository;
import appbeta.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PostService postService;
	
	private EntityNotFoundException getUserNotFoundException(Long id) {
		return new EntityNotFoundException("User id " + id + " has not been found");
	}
	
	private EntityNotFoundException getUserNotFoundException(User user ) {
		return getUserNotFoundException(user.getId());
	}

	@Override
	@Transactional
	public void add(User user) {
		user.setId(null);
		userRepository.save(user);		
	}
	
	@Override
	@Transactional
	public void updateUser(User user) {
		if (userRepository.existsById(user.getId())) {
			userRepository.save(user);
		} else {
			throw getUserNotFoundException(user);
		}
	}

	@Override
	@Transactional
	public void remove(User user) {
		postService.removeByUserId(user.getId());
		userRepository.delete(user);
	}
	
	@Override
	@Transactional
	public void removeById(Long id) {
		postService.removeByUserId(id);
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
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