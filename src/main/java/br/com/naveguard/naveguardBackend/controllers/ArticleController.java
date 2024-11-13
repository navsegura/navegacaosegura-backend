package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.dtos.ArticleDTOResponse;
import br.com.naveguard.naveguardBackend.services.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/article")
@Tag(name = "Artigo - Necessita autenticação")
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os artigos registrados.")
    public List<ArticleDTOResponse> getArticles() {

        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Retorna artigo com id especificado.")
    public ArticleDTOResponse getArticleById(@PathVariable Long id) {
        var article = service.findById(id);
        return new ArticleDTOResponse(article.id(),article.title(),article.content(),article.urlPhoto(),article.author());
    }

    @PostMapping("/new")
    @Operation(summary = "Inserir", description = "Insere um artigo.")
    public ArticleDTOResponse newArticle(@RequestBody @Valid ArticleDTO dto) {
        var article = service.insert(dto);
        return new ArticleDTOResponse(article.id(), article.title(), article.content(), article.urlPhoto(), article.author());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Editar", description = "Edita artigo com id especificado.")
    public ArticleDTOResponse updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO dto) {
        var article = service.update(id,dto);
        return new ArticleDTOResponse(article.id(), article.title(), article.content(), article.urlPhoto(), article.author());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir", description = "Exclui artigo com id especificado.")
    public ResponseEntity<ArticleDTOResponse> deleteArticleById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
