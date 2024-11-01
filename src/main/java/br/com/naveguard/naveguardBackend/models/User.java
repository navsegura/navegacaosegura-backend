package br.com.naveguard.naveguardBackend.models;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.models.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name= "tb_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthDay;
    private String urlPhoto;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String bio;

    @ManyToMany
    @JoinTable(name = "tb_tutorial_media",
            joinColumns = @JoinColumn(name = "tutorial_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> medias = new HashSet<>();
    @OneToMany(mappedBy = "author")
    private List<Tutorial> tutorials = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_parent_kid",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "kid_id"))
    private Set<User> kids = new HashSet<>();



    public User(UserMinDTO dto) {
        this.id = dto.id();
        this.name = dto.name();
        this.email = dto.email();
        password = null;
        this.birthDay = dto.birthDay();
        this.urlPhoto = dto.urlPhoto();
        this.gender = dto.gender();
        this.bio = dto.bio();
        this.tutorials = tutorials;
    }
    //    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//    }

}
