package br.com.naveguard.naveguardBackend.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutorialInsertDTO {
	private Long id;
    @NotBlank(message = "O título não pode ser vazio.")
    @Size(min = 1, max = 100)
    private String title;
    @NotBlank(message = "O conteúdo não pode ser vazio.")
    @Size(min = 250)
    private String content;
    @NotNull(message = "O tutorial deve ter um autor")
    @Positive(message = "O id do autor deve ser positivo")
    private Long authorId;
    private List<Long> medias = new ArrayList<>();
	
}
         
