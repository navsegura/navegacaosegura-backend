package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.MediaDTO;
import br.com.naveguard.naveguardBackend.services.MediaService;
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
@RequestMapping("api/media")
@Tag(name = "Mídia - Necessita autenticação")
public class MediaController {
    @Autowired
    MediaService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos as mídias registrados.")
    public ResponseEntity<Page<MediaDTO>> getMedia(Pageable pageable) {
        Page<MediaDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna mídia com id especificado.")
    public ResponseEntity<MediaDTO> getMediaById(@PathVariable Long id) {
        var media = service.findById(id);
        return ResponseEntity.ok(media);
    }

    @PostMapping
    @Operation(summary = "Inserir", description = "Insere uma mídia.")
    public ResponseEntity<MediaDTO> insertMedia(@RequestBody @Valid MediaDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita mídia com id especificado.")
    public ResponseEntity<MediaDTO> updateMedia(@PathVariable Long id, @Valid @RequestBody MediaDTO dto) {
        var media = service.update(id,dto);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui mídia com id especificado.")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}