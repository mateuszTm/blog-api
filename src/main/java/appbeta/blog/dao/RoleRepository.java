package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;

import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository <Role, Long> {

	public List <Role> findByUsers(User user);
	
}
