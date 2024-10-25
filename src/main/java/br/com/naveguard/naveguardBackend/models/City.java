package br.com.naveguard.naveguardBackend.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city")

public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public City() {
    }

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
