package br.com.naveguard.naveguardBackend.models;
import java.util.HashSet;
import java.util.Set;

import br.com.naveguard.naveguardBackend.dtos.MediaDTO;
import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(name = "tb_tutorial_media",
            joinColumns = @JoinColumn(name = "tutorial_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> medias = new HashSet<>();

	public Tutorial(TutorialDTO dto) {
		id = dto.getId();
		title = dto.getTitle();
		content = dto.getContent();
		author = new User(dto.getAuthor());
		for(MediaDTO item : dto.getMedias()) {
			medias.add(new Media(item));
		}
	}
    
    
}
