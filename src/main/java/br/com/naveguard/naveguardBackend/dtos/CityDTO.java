package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.City;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
	private Long id;
    @NotBlank(message = "Nome da cidade não pode estar vazio")
    private String name;
    @NotBlank(message = "Nome do estado não pode estar vazio")
    private String state;
    
    public CityDTO(City entity) {
		id = entity.getId();
		name = entity.getName();
		state = entity.getState().getName();
	}
    
    
}
        

