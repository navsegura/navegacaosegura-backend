package br.com.naveguard.naveguardBackend.models;
import java.util.HashSet;
import java.util.Set;

import br.com.naveguard.naveguardBackend.dtos.RoleDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    
    public Role(RoleDTO dto) {
    	id = dto.id();
    	authority = dto.authority();
    	for(UserMinDTO item : dto.users()) {
    		users.add(new User(item));
    	}
    }
}
