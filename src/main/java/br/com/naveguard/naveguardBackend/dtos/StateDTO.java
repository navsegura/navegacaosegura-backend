package br.com.naveguard.naveguardBackend.dtos;

import java.util.List;

import br.com.naveguard.naveguardBackend.models.State;
import jakarta.validation.constraints.NotBlank;

public record StateDTO(
         Long id,
         @NotBlank(message = "Nome do estado não pode ser vazio")
         String name,
         List<CityDTO> cities,
         List<UserMinDTO> users
) {
	public StateDTO(State entity) {
		this(entity.getId(), entity.getName(), entity.getCities().stream().map(x -> new CityDTO(x)).toList(), 
				entity.getUsers().stream().map(x -> new UserMinDTO(x)).toList());
	}
}
