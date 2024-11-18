package br.com.naveguard.naveguardBackend.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.naveguard.naveguardBackend.models.City;
import br.com.naveguard.naveguardBackend.models.State;
import br.com.naveguard.naveguardBackend.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StateDTO {
         
	private Long id;
    @NotBlank(message = "Nome do estado não pode ser vazio")
    private String name;
    private List<CityDTO> cities = new ArrayList<>();
    private List<UserMinDTO> users = new ArrayList<>();
    
    public StateDTO(State entity) {
    	id = entity.getId();
    	name = entity.getName();
    	for(City city : entity.getCities()) {
    		cities.add(new CityDTO(city));
    	}
    	
    	for(User user : entity.getUsers()) {
    		users.add(new UserMinDTO(user));
    	}
    }
}