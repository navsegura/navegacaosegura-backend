package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaDTO {
	private Long id;
    private String url;
	
    public MediaDTO(Media entity) {
		id = entity.getId();
		url = entity.getUrl();
		
	}
    
    
}
        

