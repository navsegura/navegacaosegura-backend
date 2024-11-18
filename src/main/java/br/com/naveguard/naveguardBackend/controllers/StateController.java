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

import br.com.naveguard.naveguardBackend.dtos.StateDTO;
import br.com.naveguard.naveguardBackend.services.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/state")
@Tag(name = "Estado - Necessita autenticação")
public class StateController {

    @Autowired
    StateService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os estados geográficos registrados.")
    public ResponseEntity<List<StateDTO>> getState() {
        List<StateDTO> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna tutorial com id especificado.")
    public ResponseEntity<StateDTO> getStateById(@PathVariable long id) {
        var state = service .findById(id);
        return ResponseEntity.ok(state);
    }

    @PostMapping
    @Operation(summary = "Inserir", description = "Insere um estado.")
    public ResponseEntity<StateDTO> insertState(@RequestBody @Valid StateDTO stateDto) {
        stateDto = service.insert(stateDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(stateDto.getId()).toUri();
        return ResponseEntity.created(uri).body(stateDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita estado com id especificado.")
    public ResponseEntity<StateDTO> updateState(@PathVariable long id, @Valid @RequestBody StateDTO dto) {
        var state = service.update(id, dto);
        return ResponseEntity.ok(state);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui estado com id especificado.")
    public ResponseEntity<Void> deleteState(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
