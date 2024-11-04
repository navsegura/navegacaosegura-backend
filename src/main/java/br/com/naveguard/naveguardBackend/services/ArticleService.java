package br.com.naveguard.naveguardBackend.services;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.models.Article;
import br.com.naveguard.naveguardBackend.repositories.ArticleRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository repository;

	@Transactional(readOnly = true)
	public ArticleDTO findById(Long id) {
		Article entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ArticleDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ArticleDTOResponse> findAll() {
		List<Article> entity = repository.findAll();
		return entity.stream()
				.map(a -> new ArticleDTOResponse(a.getId(),a.getTitle(),a.getContent(), a.getUrlPhoto(),null))
				.toList();
	}

	@Transactional
	public ArticleDTO insert(ArticleDTO dto) {
		Article entity = dtoToEntity(dto);
		entity.setId(null);
		entity = repository.save(entity);
		return new ArticleDTO(entity);
	}

	@Transactional
	public ArticleDTO update(Long id, ArticleDTO dto) {
		try {
			Article entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto);
			entity = repository.save(entity);
			return new ArticleDTO(entity);
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
	
	public Article dtoToEntity(ArticleDTO dto) {
        return new Article(dto);
	}
}
