package br.com.naveguard.naveguardBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.StateDTO;
import br.com.naveguard.naveguardBackend.models.State;
import br.com.naveguard.naveguardBackend.repositories.StateRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StateService {
	@Autowired
	private StateRepository repository;

	@Transactional(readOnly = true)
	public StateDTO findById(Long id) {
		State entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new StateDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<StateDTO> findAllPaged(Pageable pageable) {
		Page<State> entity = repository.findAll(pageable);
		return entity.map(x -> new StateDTO(x));
	}

	@Transactional
	public StateDTO insert(StateDTO dto) {
		State entity = dtoToEntity(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new StateDTO(entity);
	}

	@Transactional
	public StateDTO update(Long id, StateDTO dto) {
		try {
			State entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto);
			entity = repository.save(entity);
			return new StateDTO(entity);
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
	
	public State dtoToEntity(StateDTO dto) {
		State entity = new State(dto);
		return entity;
	}
}
