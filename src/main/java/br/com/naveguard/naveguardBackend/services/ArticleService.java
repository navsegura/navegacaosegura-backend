package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.dtos.ArticleDTOResponse;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.models.Article;
import br.com.naveguard.naveguardBackend.models.User;
import br.com.naveguard.naveguardBackend.repositories.ArticleRepository;
import br.com.naveguard.naveguardBackend.repositories.UserRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public ArticleDTOResponse findById(Long id) {
		Article entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		ArticleDTOResponse dto = new ArticleDTOResponse(entity.getId(), entity.getTitle(),
				entity.getContent(), entity.getUrlPhoto(), new UserMinDTO(entity.getAuthor()));
		return dto;
	}

	@Transactional(readOnly = true)
	public List<ArticleDTOResponse> findAll() {
		List<Article> entity = repository.findAll();
		return entity.stream()
				.map(item -> new ArticleDTOResponse(item.getId(), item.getTitle(), item.getContent(),
						item.getUrlPhoto(), new UserMinDTO(item.getAuthor()))).toList();
	}

	@Transactional
	public ArticleDTOResponse insert(ArticleDTO dto) {
		User user = userRepository.findById(dto.authorId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não existe!"));
		Article entity = new Article(dto, user);
		entity.setId(null);
		entity = repository.save(entity);
		ArticleDTOResponse dtoResponse = new ArticleDTOResponse(entity.getId(), entity.getTitle(),
				entity.getContent(), entity.getUrlPhoto(), new UserMinDTO(entity.getAuthor()));
		return dtoResponse;
	}

	@Transactional
	public ArticleDTOResponse update(Long id, ArticleDTO dto) {
		try {
			Article entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto, entity);
			repository.save(entity);
			ArticleDTOResponse dtoResponse = new ArticleDTOResponse(entity.getId(), entity.getTitle(),
					entity.getContent(), entity.getUrlPhoto(), new UserMinDTO(entity.getAuthor()));
			return dtoResponse;
			
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
	
	public Article dtoToEntity(ArticleDTO dto, Article entity) {
        entity.setTitle(dto.title());
        entity.setContent(dto.content());
        entity.setUrlPhoto(dto.urlPhoto());
        User user = userRepository.findById(dto.authorId()).orElseThrow(() -> new ResourceNotFoundException("O id do author não existe"));
        entity.setAuthor(user);
        return entity;
	}
}
