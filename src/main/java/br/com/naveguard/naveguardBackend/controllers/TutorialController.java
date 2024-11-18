package br.com.naveguard.naveguardBackend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.naveguard.naveguardBackend.dtos.TutorialDTO;
import br.com.naveguard.naveguardBackend.dtos.TutorialInsertDTO;
import br.com.naveguard.naveguardBackend.services.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/tutorial")
@Tag(name = "Tutorial - Necessita autenticação")
public class TutorialController {
    @Autowired
    TutorialService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os tutoriais registrados.")
    public ResponseEntity<List<TutorialDTO>> getTutorials() {
        List<TutorialDTO> result = service.findAll();
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
    public ResponseEntity<TutorialDTO> insertTutorial(@RequestBody @Valid TutorialInsertDTO dto) {
        TutorialDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita tutorial com id especificado.")
    public ResponseEntity<TutorialDTO> updateTutorial(@PathVariable Long id, @Valid @RequestBody TutorialInsertDTO dto) {
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