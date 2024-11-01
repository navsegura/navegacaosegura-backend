package br.com.naveguard.naveguardBackend.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_media")
@AllArgsConstructor
@NoArgsConstructor

public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToMany(mappedBy = "medias")
    private Set<Tutorial> tutorials = new HashSet<>();

}
