package br.com.naveguard.naveguardBackend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.naveguard.naveguardBackend.dtos.MediaDTO;
import br.com.naveguard.naveguardBackend.services.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/media")
@Tag(name = "Mídia - Necessita autenticação")
public class MediaController {
    @Autowired
    MediaService service;
    
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos as mídias registrados.")
    public ResponseEntity<List<MediaDTO>> getMedia() {
        List<MediaDTO> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna mídia com id especificado.")
    public ResponseEntity<MediaDTO> getMediaById(@PathVariable Long id) {
        var media = service.findById(id);
        return ResponseEntity.ok(media);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Inserir", description = "Insere uma mídia.")
    public ResponseEntity<MediaDTO> insertMedia(@RequestBody @Valid MediaDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita mídia com id especificado.")
    public ResponseEntity<MediaDTO> updateMedia(@PathVariable Long id, @Valid @RequestBody MediaDTO dto) {
        var media = service.update(id,dto);
        return ResponseEntity.ok(media);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui mídia com id especificado.")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}