package br.com.naveguard.naveguardBackend.repositories;


import br.com.naveguard.naveguardBackend.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}