package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.City;
import jakarta.validation.constraints.NotBlank;

public record CityDTO(
        Long id,
        @NotBlank(message = "Nome da cidade não pode estar vazio")
        String name,
        StateDTO state
) {
	public CityDTO(City entity) {
		this(entity.getId(), entity.getName(), new StateDTO(entity.getState()));
	}
}
