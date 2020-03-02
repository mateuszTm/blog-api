package appbeta.blog.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

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

	@Override
	public Role getRoleByName(String name) {
		Optional <Role> role = roleRepository.findByName(name);
		return role.orElseThrow(() -> new EntityNotFoundException("Role name " + name + " has not been found"));
	}
	
	@Override
	public Set<Role> getRoleByName(Collection<String> name) {
		return roleRepository.findByNameIn(name);
	}

}
