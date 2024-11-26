package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import br.com.naveguard.naveguardBackend.dtos.TutorialInsertDTO;
import br.com.naveguard.naveguardBackend.models.Media;
import br.com.naveguard.naveguardBackend.models.Tutorial;
import br.com.naveguard.naveguardBackend.models.User;
import br.com.naveguard.naveguardBackend.repositories.MediaRepository;
import br.com.naveguard.naveguardBackend.repositories.TutorialRepository;
import br.com.naveguard.naveguardBackend.repositories.UserRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TutorialService {
	
	@Autowired
	private TutorialRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MediaRepository mediaRepository;

	@Transactional(readOnly = true)
	public TutorialDTO findById(Long id) {
		Tutorial entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new TutorialDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<TutorialDTO> findAll() {
		List<Tutorial> entity = repository.findAll();
		return entity.stream().map(x -> new TutorialDTO(x)).toList();
	}

	@Transactional
	public TutorialDTO insert(TutorialInsertDTO dto) {
		Tutorial entity = dtoToEntityInsert(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new TutorialDTO(entity);
	}

	@Transactional
	public TutorialDTO update(Long id, TutorialInsertDTO dto) {
		try {
			Tutorial entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto, entity);
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
	
	public Tutorial dtoToEntity(TutorialInsertDTO dto, Tutorial entity) {
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		User user = userRepository.findById(dto.getAuthorId())
				.orElseThrow(() -> new ResourceNotFoundException("Este autor não foi encontrado"));
		entity.setAuthor(user);
		for(Long mediaId : dto.getMedias()) {
			Media media = mediaRepository.findById(mediaId)
					.orElseThrow(() -> new ResourceNotFoundException("Esta media não foi encontrada"));
			entity.getMedias().add(media);
		}
		
		return entity;
	}
	
	public Tutorial dtoToEntityInsert(TutorialInsertDTO dto) {
		Tutorial entity = new Tutorial();
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		User user = userRepository.findById(dto.getAuthorId())
				.orElseThrow(() -> new ResourceNotFoundException("Este autor não foi encontrado"));
		entity.setAuthor(user);
		for(Long mediaId : dto.getMedias()) {
			Media media = mediaRepository.findById(mediaId)
					.orElseThrow(() -> new ResourceNotFoundException("Esta media não foi encontrada"));
			entity.getMedias().add(media);
		}
		return entity;
	}
}
