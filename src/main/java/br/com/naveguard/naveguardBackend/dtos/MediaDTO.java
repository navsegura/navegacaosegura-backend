package br.com.naveguard.naveguardBackend.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.naveguard.naveguardBackend.models.Media;

public record MediaDTO(
        Long id,
        String url,
        Set<TutorialDTO> tutorials
) {
	public MediaDTO(Media entity) {
		this(entity.getId(), entity.getUrl(), 
				entity.getTutorials().stream().map(x -> new TutorialDTO(x)).collect(Collectors.toSet()));
	}
}
