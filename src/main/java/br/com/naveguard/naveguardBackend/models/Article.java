package br.com.naveguard.naveguardBackend.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="tb_article")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String urlPhoto;

    public Article() {
    }

    public Article(Long id, String title, String content, String urlPhoto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.urlPhoto = urlPhoto;
    }
}
