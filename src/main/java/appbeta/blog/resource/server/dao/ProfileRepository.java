package appbeta.blog.resource.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.resource.server.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	public Optional <Profile> findByLogin(String login);
	
	public Optional <Profile> findById(Long id);
}
