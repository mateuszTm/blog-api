package appbeta.blog.resource.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.resource.server.entity.Role;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository <Role, Long> {

	public Optional <Role> findByName(String name);
	
	public Set<Role> findByNameIn(Collection<String> names);
}