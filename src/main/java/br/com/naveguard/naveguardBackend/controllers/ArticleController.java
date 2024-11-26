package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.dtos.ArticleDTOResponse;
import br.com.naveguard.naveguardBackend.services.ArticleService;
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
@RequestMapping("api/article")
@Tag(name = "Artigo - Necessita autenticação")
public class ArticleController {

    @Autowired
    ArticleService service;
    
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os artigos registrados.")
    public ResponseEntity<List<ArticleDTOResponse>> getArticles() {
    	var article = service.findAll();
        return ResponseEntity.ok().body(article);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna artigo com id especificado.")
    public ResponseEntity<ArticleDTOResponse> getArticleById(@PathVariable Long id) {
        var article = service.findById(id);
        return ResponseEntity.ok().body(article);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Inserir", description = "Insere um artigo.")
    public ResponseEntity<ArticleDTOResponse> newArticle(@Valid @RequestBody ArticleDTO dto) {
        var article = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(article.id()).toUri();
        return ResponseEntity.created(uri).body(article);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Edita artigo com id especificado.")
    public ResponseEntity<ArticleDTOResponse> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO dto) {
        var article = service.update(id, dto);
        return ResponseEntity.ok().body(article);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui artigo com id especificado.")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
