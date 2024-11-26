package br.com.naveguard.naveguardBackend.dtos;



import br.com.naveguard.naveguardBackend.models.Role;

public record RoleMinDTO(
        Long id,
        String authority
) {
	public RoleMinDTO(Role entity) {
		this(entity.getId(), entity.getAuthority());
	}
}
