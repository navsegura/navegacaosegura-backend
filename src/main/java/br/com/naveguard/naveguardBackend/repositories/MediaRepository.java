package br.com.naveguard.naveguardBackend.repositories;


import br.com.naveguard.naveguardBackend.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
