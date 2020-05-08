package appbeta.blog.resource.server.service;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import appbeta.blog.resource.server.dao.UserRepository;
import appbeta.blog.resource.server.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PostService postService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public void updateUser(User user) {
		if (userRepository.existsById(user.getId())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Role " + user + " has not been found");
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