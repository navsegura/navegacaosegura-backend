package br.com.naveguard.naveguardBackend.models;
import java.util.ArrayList;
import java.util.List;

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import br.com.naveguard.naveguardBackend.dtos.StateDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_state")
@AllArgsConstructor
@NoArgsConstructor

public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "state")
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "state")
    private List<User> users = new ArrayList<>();
    
    public State(StateDTO dto) {
    	id = dto.id();
    	name = dto.name();
    	for(CityDTO item : dto.cities()) {
    		cities.add(new City(item));
    	}
    	
    	for(UserMinDTO item : dto.users()) {
    		users.add(new User(item));
    	}
    }
}

