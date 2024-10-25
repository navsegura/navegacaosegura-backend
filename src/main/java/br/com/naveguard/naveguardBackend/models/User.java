package br.com.naveguard.naveguardBackend.models;
import br.com.naveguard.naveguardBackend.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birth_day;
    private String urlPhoto;
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



}
