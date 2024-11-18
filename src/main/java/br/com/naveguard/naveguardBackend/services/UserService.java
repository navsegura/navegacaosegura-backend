package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.UserDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.models.User;
import br.com.naveguard.naveguardBackend.repositories.UserRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public UserMinDTO findById(Long id) {
		User entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new UserMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<UserMinDTO> findAll() {
		List<User> allUserList = repository.findAll();
		List<UserMinDTO> allUserListDTO= allUserList.stream()
				.map(u -> new UserMinDTO(u))
				.toList();
		return allUserListDTO;
	}

	@Transactional
	public UserMinDTO insert(UserDTO dto) {
		User entity = new User(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new UserMinDTO(entity);
	}

	@Transactional
	public UserMinDTO update(Long id, UserDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserMinDTO(entity);
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
	
	public User dtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.name());
		entity.setEmail(dto.email());
		entity.setPassword(dto.password());
		entity.setPhone(dto.phone());
		entity.setBirthDay(dto.birthDay());
		entity.setUrlPhoto(dto.urlPhoto());
		entity.setGender(dto.gender());
		entity.setBio(dto.bio());
		return entity;
	}
}
