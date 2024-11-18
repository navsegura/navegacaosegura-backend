package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import br.com.naveguard.naveguardBackend.models.City;
import br.com.naveguard.naveguardBackend.models.State;
import br.com.naveguard.naveguardBackend.repositories.CityRepository;
import br.com.naveguard.naveguardBackend.repositories.StateRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CityService {
	@Autowired
	private CityRepository repository;
	
	@Autowired 
	private StateRepository stateRepository;

	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		City entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new CityDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> entity = repository.findAll();
		return entity.stream().map(x -> new CityDTO(x)).toList();
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City(dto);
		State state = stateRepository.findByName(dto.getState());
		entity.setState(state);
		entity.setId(null);
		entity = repository.save(entity);
		return new CityDTO(entity);
		
	}

	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City entity = repository.getReferenceById(id);
			entity = dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new CityDTO(entity);
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
	
	public City dtoToEntity(CityDTO dto, City entity) {
		entity.setName(dto.getName());
		return entity;
	}
}
