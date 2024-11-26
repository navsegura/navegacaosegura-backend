package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.UserDTO;
import br.com.naveguard.naveguardBackend.dtos.UserMinDTO;
import br.com.naveguard.naveguardBackend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuário - Necessita autenticação")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os usuários registrados.")
    public ResponseEntity<List<UserMinDTO>> getAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna usuário com id especificado.")
    public ResponseEntity<UserMinDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(value = "/me")
	public ResponseEntity<UserMinDTO> findMe() { 
		UserMinDTO user = service.findMe();
		
		return ResponseEntity.ok().body(user);
	}

    
    @PostMapping
    @Operation(summary = "Inserir", description = "Insere um usuário.")
    public ResponseEntity<UserMinDTO> createUser(@RequestBody @Valid UserDTO user) {
        UserMinDTO userCreated = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userCreated.id()).toUri();
        return ResponseEntity.created(uri).body(userCreated);
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Operation(summary = "Editar", description = "Edita usuário com id especificado.")
    public ResponseEntity<UserMinDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO user) {
        UserMinDTO userUpdated = service.update(id, user);
        return ResponseEntity.ok(userUpdated);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui usuário com id especificado.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
