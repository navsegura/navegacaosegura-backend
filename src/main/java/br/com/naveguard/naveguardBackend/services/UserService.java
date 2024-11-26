package br.com.naveguard.naveguardBackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.naveguard.naveguardBackend.dtos.UserDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.models.City;
import br.com.naveguard.naveguardBackend.models.Role;
import br.com.naveguard.naveguardBackend.models.State;
import br.com.naveguard.naveguardBackend.models.User;
import br.com.naveguard.naveguardBackend.projections.UserDetailsProjection;
import br.com.naveguard.naveguardBackend.repositories.CityRepository;
import br.com.naveguard.naveguardBackend.repositories.RoleRepository;
import br.com.naveguard.naveguardBackend.repositories.StateRepository;
import br.com.naveguard.naveguardBackend.repositories.UserRepository;
import br.com.naveguard.naveguardBackend.services.exceptions.DatabaseException;
import br.com.naveguard.naveguardBackend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;

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
	
	@Transactional(readOnly = true)
	public UserMinDTO findMe() {
		User user = authService.authenticated();
		return new UserMinDTO(user);
	}

	@Transactional
	public UserMinDTO insert(UserDTO dto) {
		User entity = new User(dto);
		entity.setId(null);
		entity.setPassword(passwordEnconder.encode(dto.password()));
		entity.getRoles().clear();
		
		Role role = roleRepository.findByAuthority("ROLE_CLIENT");
		if(role == null) {
			throw new ResourceNotFoundException("Authority não encontrado");
		}
		entity.getRoles().add(role);
		
		City city = cityRepository.findByName(dto.city());
		State state = stateRepository.findByName(dto.state());
		
		if(city == null || state == null) {
			throw new ResourceNotFoundException("Nome de cidade ou estado não existe");
		}
		
		entity.setCity(city);
		entity.setState(state);
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if(result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		
		for(UserDetailsProjection p : result) {
			user.addRoles(new Role(p.getRoleId(), p.getAuthority()));
		}
		
		return user;
	}
}
