package far.insp.sirhat.presentation.controller;


import far.insp.sirhat.persistance.entity.Article;
import far.insp.sirhat.service.face.ArticleServiceFace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private final ArticleServiceFace articleService;

    public ArticleController(ArticleServiceFace articleService) {
        this.articleService = articleService;
    }

    @GetMapping("one")
    public ResponseEntity<Article> getArticleById(@RequestParam("id")Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.findArticleById(id));
    }

}
