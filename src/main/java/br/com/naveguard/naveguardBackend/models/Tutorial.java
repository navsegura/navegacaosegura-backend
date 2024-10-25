package br.com.naveguard.naveguardBackend.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tutorial")

public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Tutorial() {
    }

    public Tutorial(Long id, String title, String content) {
        this.id = id;
        title = title;
        content = content;
    }
}
