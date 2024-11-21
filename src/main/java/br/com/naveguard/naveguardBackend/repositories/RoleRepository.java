package br.com.naveguard.naveguardBackend.repositories;

import br.com.naveguard.naveguardBackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByAuthority(String Authority);
}
