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
@Table(name = "tb_tutorial")
@AllArgsConstructor
@NoArgsConstructor

public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(name = "tb_tutorial_media",
            joinColumns = @JoinColumn(name = "tutorial_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> medias = new HashSet<>();
}
