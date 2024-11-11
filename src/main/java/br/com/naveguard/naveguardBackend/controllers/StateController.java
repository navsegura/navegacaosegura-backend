package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.StateDTO;
import br.com.naveguard.naveguardBackend.services.StateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/state")
public class StateController {

    @Autowired
    StateService service;

    @GetMapping
    public ResponseEntity<Page<StateDTO>> getState(Pageable pageable) {
        Page<StateDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDTO> getStateById(@PathVariable long id) {
        var state = service .findById(id);
        return ResponseEntity.ok(state);
    }

    @PostMapping
    public ResponseEntity<StateDTO> insertState(@RequestBody @Valid StateDTO stateDto) {
        stateDto = service.insert(stateDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(stateDto.id()).toUri();
        return ResponseEntity.created(uri).body(stateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateDTO> updateState(@PathVariable long id, @Valid @RequestBody StateDTO dto) {
        var state = service.update(id, dto);
        return ResponseEntity.ok(state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
