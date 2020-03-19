package appbeta.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import appbeta.blog.dao.UserRepository;
import appbeta.blog.entity.User;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByLogin(name);
		if (optional.isPresent()) {
			User user = optional.get();
			Set<SimpleGrantedAuthority> authorities = user.getRoles()
					.stream()
					.map(r -> new SimpleGrantedAuthority(r.getName()))
					.collect(Collectors.toSet());
			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
		}
		throw new UsernameNotFoundException("User with login '" + name + "' has not been found");
	}
}
