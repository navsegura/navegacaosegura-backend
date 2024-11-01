package br.com.naveguard.naveguardBackend.models;
import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name="tb_article")
@AllArgsConstructor
@NoArgsConstructor

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String urlPhoto;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Article(ArticleDTO article) {
        this.id = article.id();
        this.title = article.title();
        this.content = article.content();
        this.urlPhoto = article.urlPhoto();
        this.author = new User(article.author());
    }
}
