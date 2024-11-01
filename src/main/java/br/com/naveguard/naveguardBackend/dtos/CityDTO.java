package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.State;
import jakarta.validation.constraints.NotBlank;

public record CityDTO(
        Long id,
        @NotBlank(message = "Nome da cidade não pode estar vazio")
        String name,
        State state
) {
}
