package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import appbeta.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByLogin(String login);
	
	public Optional <User> findById(Long id);
}
