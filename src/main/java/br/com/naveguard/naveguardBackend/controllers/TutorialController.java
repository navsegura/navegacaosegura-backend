package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import br.com.naveguard.naveguardBackend.services.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;


@RestController
@RequestMapping("api/tutorial")
@Tag(name = "Tutorial - Necessita autenticação")
public class TutorialController {
    @Autowired
    TutorialService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os tutoriais registrados.")
    public ResponseEntity<Page<TutorialDTO>> getTutorials(Pageable pageable) {
        Page<TutorialDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna tutorial com id especificado.")
    public ResponseEntity<TutorialDTO> getTutorial(@PathVariable Long id) {
        var tutorial = service.findById(id);
        return ResponseEntity.ok(tutorial);
    }

    @PostMapping
    @Operation(summary = "Inserir", description = "Insere um tutorial.")
    public ResponseEntity<TutorialDTO> insertTutorial(@RequestBody @Valid TutorialDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita tutorial com id especificado.")
    public ResponseEntity<TutorialDTO> updateTutorial(@PathVariable Long id, @Valid @RequestBody TutorialDTO dto) {
        var tutorial = service.update(id,dto);
        return ResponseEntity.ok(tutorial);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui tutorial com id especificado.")
    public ResponseEntity<Void> deleteTutorial(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}