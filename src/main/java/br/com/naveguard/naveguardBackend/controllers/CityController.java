package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.CityDTO;
import br.com.naveguard.naveguardBackend.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/city")
public class CityController {
    @Autowired
    CityService service;

    @GetMapping
    public ResponseEntity<Page<CityDTO>> getCity(Pageable pageable) {
        Page<CityDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getMediaById(@PathVariable Long id) {
        var city = service.findById(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<CityDTO> insertCity(@RequestBody @Valid CityDTO cityDto) {
        cityDto = service.insert(cityDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cityDto.id()).toUri();
        return ResponseEntity.created(uri).body(cityDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id, @Valid @RequestBody CityDTO dto) {
        var city = service.update(id,dto);
        return ResponseEntity.ok(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}