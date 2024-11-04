package br.com.naveguard.naveguardBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleDTOResponse(Long id,
                                 String title,
                                 String content,
                                 String urlPhoto,
                                 UserMinDTO author) {
}
