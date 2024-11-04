package br.com.naveguard.naveguardBackend.controllers;

import br.com.naveguard.naveguardBackend.dtos.ArticleDTO;
import br.com.naveguard.naveguardBackend.dtos.ArticleDTOResponse;
import br.com.naveguard.naveguardBackend.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/article")
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping
    public List<ArticleDTOResponse> getArticles() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public ArticleDTOResponse getArticleById(@PathVariable Long id) {
        var article = service.findById(id);
        return new ArticleDTOResponse(article.id(),article.title(),article.content(),article.urlPhoto(),article.author());
    }

    @PostMapping("/new")
    public ArticleDTOResponse newArticle(@RequestBody ArticleDTO dto) {
        var article = service.insert(dto);
        return new ArticleDTOResponse(article.id(), article.title(), article.content(), article.urlPhoto(), article.author());
    }

    @PutMapping("/update/{id}")
    public ArticleDTOResponse updateArticle(@PathVariable Long id, @RequestBody ArticleDTO dto) {
        var article = service.update(id,dto);
        return new ArticleDTOResponse(article.id(), article.title(), article.content(), article.urlPhoto(), article.author());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArticleDTOResponse> deleteArticleById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
