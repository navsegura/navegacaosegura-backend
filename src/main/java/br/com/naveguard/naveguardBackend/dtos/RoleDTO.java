package br.com.naveguard.naveguardBackend.dtos;



import java.util.Set;

public record RoleDTO(
        Long id,
        String authority,
        Set<UserMinDTO> users
) {
}
