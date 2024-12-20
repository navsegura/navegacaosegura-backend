package br.com.naveguard.naveguardBackend.models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import br.com.naveguard.naveguardBackend.dtos.UserDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.models.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "tb_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birthDay;
    private String urlPhoto;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String bio;

    @OneToMany(mappedBy = "author")
    private List<Tutorial> tutorials = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(UserMinDTO dto) {
        this.id = dto.id();
        this.name = dto.name();
        this.email = dto.email();
        password = null;
        this.birthDay = dto.birthDay();
        this.urlPhoto = dto.urlPhoto();
        this.gender = dto.gender();
        this.bio = dto.bio();
    }
    
    public User(UserDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.password = dto.password();
        this.birthDay = dto.birthDay();
        this.phone = dto.phone();
        this.urlPhoto = dto.urlPhoto();
        this.gender = dto.gender();
        this.bio = dto.bio();
    }
    
    public void addRoles(Role role) {
		roles.add(role);
	}
	
    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    


}
