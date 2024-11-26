package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ArticleDTO(
        Long id,
        
        @NotBlank(message = "O título não pode ser vazio.")
        @Size(min = 1, max = 100)
        String title,
        
        @NotBlank(message = "O conteúdo não pode ser vazio.")
        @Size(min = 250, message = "O conteúdo deve ter no mínimo 250 caracteres")
        String content,
        
        String urlPhoto,
        
        @NotNull(message = "O campo é obrigatório")
        @Positive(message = "O id tem que ser um número positivo")
        Long authorId
) {
	public ArticleDTO(Article entity) {
		this(entity.getId(), entity.getTitle(), entity.getContent(), entity.getUrlPhoto(),
				entity.getAuthor().getId());
	}
}
