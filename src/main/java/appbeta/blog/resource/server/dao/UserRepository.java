package appbeta.blog.resource.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.resource.server.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional <User> findByLogin(String login);
	
	public Optional <User> findById(Long id);
}
