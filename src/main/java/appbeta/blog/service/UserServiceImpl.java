package appbeta.blog.service;

import java.util.List;
import java.util.Optional;
import appbeta.blog.dao.UserRepository;
import appbeta.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void save(User user) {
		userRepository.save(user);		
	}

	@Override
	@Transactional
	public void remove(User user) {
		userRepository.delete(user);
		
	}
	
	@Override
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
		return userRepository.findByLogin(login);
	}
	
	@Override
	@Transactional
	public Optional <User> findUserById(Long id) {
		return userRepository.findById(id);
	}
}
