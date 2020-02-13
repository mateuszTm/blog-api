package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import appbeta.blog.entity.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {

}
