package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.MediaDTO;
import br.com.naveguard.naveguardBackend.models.Media;
import br.com.naveguard.naveguardBackend.repositories.MediaRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MediaService {
	@Autowired
	private MediaRepository repository;

	@Transactional(readOnly = true)
	public MediaDTO findById(Long id) {
		Media entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new MediaDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<MediaDTO> findAll() {
		List<Media> entity = repository.findAll();
		return entity.stream().map(x -> new MediaDTO(x)).toList();
	}

	@Transactional
	public MediaDTO insert(MediaDTO dto) {
		Media entity = new Media(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new MediaDTO(entity);
	}

	@Transactional
	public MediaDTO update(Long id, MediaDTO dto) {
		try {
			Media entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new MediaDTO(entity);
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
	
	public Media dtoToEntity(MediaDTO dto, Media entity) {
		entity.setUrl(dto.getUrl());
		return entity;
	}
}
