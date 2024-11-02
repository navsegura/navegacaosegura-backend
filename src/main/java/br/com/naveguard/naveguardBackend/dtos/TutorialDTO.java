package br.com.naveguard.naveguardBackend.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.naveguard.naveguardBackend.models.Tutorial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TutorialDTO(
         Long id,
         @NotBlank(message = "O título não pode ser vazio.")
         @Size(min = 1, max = 100)
         String title,
         @NotBlank(message = "O conteúdo não pode ser vazio.")
         @Size(min = 250)
         String content,
         UserMinDTO author,
         Set<MediaDTO> medias
) {
	public TutorialDTO(Tutorial entity) {
		this(entity.getId(), entity.getTitle(), entity.getContent(), new UserMinDTO(entity.getAuthor()),
				entity.getMedias().stream().map(x -> new MediaDTO(x)).collect(Collectors.toSet()));
	}
}
