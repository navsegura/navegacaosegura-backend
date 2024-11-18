package br.com.naveguard.naveguardBackend.models;
import java.util.ArrayList;
import java.util.List;

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import br.com.naveguard.naveguardBackend.dtos.StateDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import jakarta.persistence.Column;
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
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "state")
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "state")
    private List<User> users = new ArrayList<>();
    
    public State(StateDTO dto) {
    	id = dto.getId();
    	name = dto.getName();
    	for(CityDTO item : dto.getCities()) {
    		cities.add(new City(item));
    	}
    	
    	for(UserMinDTO item : dto.getUsers()) {
    		users.add(new User(item));
    	}
    }
}

