package appbeta.blog.resource.server.service;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import appbeta.blog.resource.server.dao.ProfileRepository;
import appbeta.blog.resource.server.entity.Profile;

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
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository userRepository;
	
	@Autowired 
	private PostService postService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private EntityNotFoundException getProfileNotFoundException(Long id) {
		return new EntityNotFoundException("User id " + id + " has not been found");
	}
	
	private EntityNotFoundException getUserNotFoundException(Profile user ) {
		return getProfileNotFoundException(user.getId());
	}

	@Override
	@Transactional
	public void add(Profile user) {
		user.setId(null);
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public void updateProfile(Profile user) {
		if (userRepository.existsById(user.getId())) {
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Role " + user + " has not been found");
		}
	}

	@Override
	@Transactional
	public void remove(Profile user) {
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
	public Page<Profile> getAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Profile getByLogin(String login) {
		return userRepository.findByLogin(login).orElseThrow(() -> (new EntityNotFoundException("Profile with login '" + login + "' has not been found")));
	}
	
	@Override
	@Transactional
	public Profile getById(Long id){
		return userRepository.findById(id).orElseThrow(() -> getProfileNotFoundException(id));
	}

}