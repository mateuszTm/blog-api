package appbeta.blog.service;

import java.util.List;

import appbeta.blog.dao.RoleRepository;
import appbeta.blog.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	@Transactional
	public void remove(Role role) {
		roleRepository.delete(role);
	}

	// TODO sortowanie itp
	@Override
	@Transactional
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
