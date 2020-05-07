package appbeta.blog.resource.server.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import appbeta.blog.resource.server.dao.RoleRepository;
import appbeta.blog.resource.server.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void add(Role role) {
		role.setId(null);
		roleRepository.save(role);
	}
	
	@Override
	public void update(Role role) {
		if (roleRepository.existsById(role.getId())) {
			roleRepository.save(role);
		} else {
			throw new EntityNotFoundException("Role " + role + " has not been found");
		}
	}

	@Override
	@Transactional
	public void remove(Role role) {
		roleRepository.delete(role);
	}

	@Override
	@Transactional
	public void removeById (Long id) {
		roleRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public Page<Role> getAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
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

	@Override
	public Role getRoleByid (long id) {
		return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role id " + id + " has not been found"));
	}
}
