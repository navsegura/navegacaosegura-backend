package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.RoleDTO;
import br.com.naveguard.naveguardBackend.dtos.RoleMinDTO;
import br.com.naveguard.naveguardBackend.models.Role;
import br.com.naveguard.naveguardBackend.repositories.RoleRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {
	@Autowired
	private RoleRepository repository;

	@Transactional(readOnly = true)
	public RoleMinDTO findById(Long id) {
		Role entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new RoleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<RoleMinDTO> findAll() {
		List<Role> list = repository.findAll();
		return list.stream().map(x -> new RoleMinDTO(x)).toList();
		
	}
	
	@Transactional(readOnly = true)
	public RoleMinDTO findByName(String name) {
		Role entity = repository.findByAuthority(name);
		if(entity == null) {
			throw new ResourceNotFoundException("Authority não encontrado");
		}
		return new RoleMinDTO(entity);
	}

	@Transactional
	public RoleDTO insert(RoleDTO dto) {
		Role entity = dtoToEntity(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new RoleDTO(entity);
	}

	@Transactional
	public RoleDTO update(Long id, RoleDTO dto) {
		try {
			Role entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto);
			entity = repository.save(entity);
			return new RoleDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
			
		}
		

	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}
	
	public Role dtoToEntity(RoleDTO dto) {
		Role entity = new Role(dto);
		return entity;
	}
}
