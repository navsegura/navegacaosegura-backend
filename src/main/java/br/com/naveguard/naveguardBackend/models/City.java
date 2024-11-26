package br.com.naveguard.naveguardBackend.models;
import java.util.ArrayList;
import java.util.List;

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_city")
@AllArgsConstructor
@NoArgsConstructor

public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    
    @OneToMany(mappedBy = "city")
    private List<User> users = new ArrayList<>();
    
    public City(CityDTO dto) {
    	id = dto.getId();
    	name = dto.getName();
    }
}
