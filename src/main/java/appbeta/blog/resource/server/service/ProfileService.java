package appbeta.blog.resource.server.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import appbeta.blog.resource.server.entity.Profile;

public interface ProfileService {

	public void add (Profile user);
	
	public void updateProfile (Profile user);
	
	public void remove (Profile user);
	
	public void removeById (Long id);
	
	public Page <Profile> getAllProfiles (Pageable pageable);
	
	public Profile findProfileByLogin (String  login);
	
	public Profile findProfileById (Long id);
}
