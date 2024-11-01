package br.com.naveguard.naveguardBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record StateDTO(
         Long id,
         @NotBlank(message = "Nome do estado não pode ser vazio")
         String name
) {
}
