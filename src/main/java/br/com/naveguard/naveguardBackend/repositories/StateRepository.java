package br.com.naveguard.naveguardBackend.repositories;

import br.com.naveguard.naveguardBackend.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
