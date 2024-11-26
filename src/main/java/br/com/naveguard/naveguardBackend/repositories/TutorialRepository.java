package br.com.naveguard.naveguardBackend.repositories;


import br.com.naveguard.naveguardBackend.models.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

}
