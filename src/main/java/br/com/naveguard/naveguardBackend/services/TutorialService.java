package br.com.naveguard.naveguardBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import br.com.naveguard.naveguardBackend.models.Tutorial;
import br.com.naveguard.naveguardBackend.repositories.TutorialRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TutorialService {
	
	@Autowired
	private TutorialRepository repository;

	@Transactional(readOnly = true)
	public TutorialDTO findById(Long id) {
		Tutorial entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new TutorialDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<TutorialDTO> findAllPaged(Pageable pageable) {
		Page<Tutorial> entity = repository.findAll(pageable);
		return entity.map(x -> new TutorialDTO(x));
	}

	@Transactional
	public TutorialDTO insert(TutorialDTO dto) {
		Tutorial entity = dtoToEntity(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new TutorialDTO(entity);
	}

	@Transactional
	public TutorialDTO update(Long id, TutorialDTO dto) {
		try {
			Tutorial entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto);
			entity = repository.save(entity);
			return new TutorialDTO(entity);
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
	
	public Tutorial dtoToEntity(TutorialDTO dto) {
		Tutorial entity = new Tutorial(dto);
		return entity;
	}
}
