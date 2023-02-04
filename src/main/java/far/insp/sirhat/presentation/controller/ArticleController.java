package far.insp.sirhat.presentation.controller;


import far.insp.sirhat.persistance.entity.Article;
import far.insp.sirhat.presentation.dto.QuantiteArticleDto;
import far.insp.sirhat.service.face.ArticleServiceFace;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private final ArticleServiceFace articleService;

    public ArticleController(ArticleServiceFace articleService) {
        this.articleService = articleService;
    }

    @GetMapping("code")
    public ResponseEntity<Article> getArticleById(@RequestParam("code") String code) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.findArticleByCode(code));
    }

    @GetMapping("stock")
    public ResponseEntity<List<Article>> stock() {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.stock());
    }

    @PostMapping("quantite/add")
    public ResponseEntity<Article> addArticleQuantite(@RequestBody @Valid QuantiteArticleDto quantiteArticleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.addQuantiteArticleById(quantiteArticleDto));
    }

}
