package br.com.naveguard.naveguardBackend.dtos;



import java.util.Set;
import java.util.stream.Collectors;

import br.com.naveguard.naveguardBackend.models.Role;

public record RoleDTO(
        Long id,
        String authority,
        Set<UserMinDTO> users
) {
	public RoleDTO(Role entity) {
		this(entity.getId(), entity.getAuthority(), 
				entity.getUsers().stream().map(x -> new UserMinDTO(x)).collect(Collectors.toSet()));
	}
}
