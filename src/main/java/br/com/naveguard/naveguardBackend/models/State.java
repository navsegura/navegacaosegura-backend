package br.com.naveguard.naveguardBackend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
}

