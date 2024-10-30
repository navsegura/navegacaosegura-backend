package br.com.naveguard.naveguardBackend.models;
import br.com.naveguard.naveguardBackend.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birth_day;
    private String urlPhoto;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String bio;

    @OneToMany(mappedBy = "author")
    private List<Tutorial> tutorials = new ArrayList<>();


    public User() {
    }

    public User(Long id, String name, String email, String password, LocalDate birth_day, String urlPhoto, Gender gender, String bio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth_day = birth_day;
        this.urlPhoto = urlPhoto;
        this.gender = gender;
        this.bio = bio;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
