package br.com.naveguard.naveguardBackend.dtos;

import br.com.naveguard.naveguardBackend.models.User;
import br.com.naveguard.naveguardBackend.models.enums.Gender;

import java.time.LocalDate;

public record UserMinDTO(Long id, String name, String email, LocalDate birthDay, String urlPhoto, Gender gender, String bio, String state, String city) {
    public UserMinDTO(User user) { this(user.getId(), user.getName(), user.getEmail(), user.getBirthDay(), user.getUrlPhoto(), user.getGender(), user.getBio(), user.getState().getName(), user.getCity().getName()); }
}
