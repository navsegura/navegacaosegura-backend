package br.com.naveguard.naveguardBackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.naveguard.naveguardBackend.dtos.RoleMinDTO;
import br.com.naveguard.naveguardBackend.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/role")
@Tag(name = "Artigo - Necessita autenticação")
public class RoleController {

	@Autowired
	private RoleService service;
	
	@GetMapping
	public ResponseEntity<List<RoleMinDTO>> findAll() {
		List<RoleMinDTO> list = service.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleMinDTO> findById(@PathVariable Long id) {
		RoleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("name/{name}")
	public ResponseEntity<RoleMinDTO> findByName(@PathVariable String name) {
		RoleMinDTO dto = service.findByName(name);
		return ResponseEntity.ok(dto);
	}
}