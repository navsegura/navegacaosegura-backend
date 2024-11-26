package br.com.naveguard.naveguardBackend.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.naveguard.naveguardBackend.models.Media;
import br.com.naveguard.naveguardBackend.models.Tutorial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutorialDTO {
	private Long id;
    @NotBlank(message = "O título não pode ser vazio.")
    @Size(min = 1, max = 100)
    private String title;
    @NotBlank(message = "O conteúdo não pode ser vazio.")
    @Size(min = 250)
    private String content;
    private UserMinDTO author;
    private List<MediaDTO> medias = new ArrayList<>();
	
    public TutorialDTO(Tutorial entity) {
		id = entity.getId();
		title = entity.getTitle();
		content = entity.getContent();
		author =  new UserMinDTO(entity.getAuthor());
		for(Media item : entity.getMedias()) {
			medias.add(new MediaDTO(item));
		}
	}
    
    
}
         
