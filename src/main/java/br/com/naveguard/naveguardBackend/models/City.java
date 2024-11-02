package br.com.naveguard.naveguardBackend.models;
import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import jakarta.persistence.*;
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
    
    public City(CityDTO dto) {
    	id = dto.id();
    	name = dto.name();
    	state = new State(dto.state());
    }
}
