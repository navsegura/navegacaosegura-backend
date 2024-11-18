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

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import br.com.naveguard.naveguardBackend.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/city")
@Tag(name = "Cidade - Necessita autenticação")
public class CityController {
    @Autowired
    CityService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos as cidades registradas.")
    public ResponseEntity<List<CityDTO>> getCity() {
        List<CityDTO> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna cidade com id especificado.")
    public ResponseEntity<CityDTO> getMediaById(@PathVariable Long id) {
        var city = service.findById(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    @Operation(summary = "Inserir", description = "Insere uma cidade.")
    public ResponseEntity<CityDTO> insertCity(@RequestBody @Valid CityDTO cityDto) {
        cityDto = service.insert(cityDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cityDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cityDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita cidade com id especificado.")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id, @Valid @RequestBody CityDTO dto) {
        var city = service.update(id,dto);
        return ResponseEntity.ok(city);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui cidade com id especificado.")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
