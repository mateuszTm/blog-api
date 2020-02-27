package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import appbeta.blog.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Long> {

	public Optional <Role> findByName(String name);
}
