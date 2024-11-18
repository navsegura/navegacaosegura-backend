package br.com.naveguard.naveguardBackend.dtos;

import java.time.LocalDate;

import br.com.naveguard.naveguardBackend.models.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "O nome não pode ser vazio")
        @Size(min = 3, max = 50, message = "O nome tem que ter entre 3 e 50 caracteres")
        String name,
        @Email(message = "Endereço de email inválido ou incorreto")
        @NotBlank(message = "O email não pode estar vazio")
        String email,
        @NotBlank(message = "O campo de senha não pode ser vazio")
        @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
        String password,
        String phone,
        @PastOrPresent(message = "Data de nascimento não pode ser futura")
        LocalDate birthDay,
        String urlPhoto,
        Gender gender,
        @Size(max = 200, message = "O tamanho da bio deve ter no máximo 200 caracteres")
        String bio
) {
}
