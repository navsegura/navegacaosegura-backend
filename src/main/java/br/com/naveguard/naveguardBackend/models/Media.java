package br.com.naveguard.naveguardBackend.models;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.naveguard.naveguardBackend.dtos.MediaDTO;
import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public Media(MediaDTO dto) {
		id = dto.id();
		url = dto.url();
		for(TutorialDTO item : dto.tutorials()) {
			tutorials.add(new Tutorial(item));
		}
		
	}
    
    

}
