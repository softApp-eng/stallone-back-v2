package far.insp.sirhat.presentation.controller;

import far.insp.sirhat.service.face.ArticleServiceFace;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final ArticleServiceFace articleService;

    public PublicController(ArticleServiceFace articleService) {
        this.articleService = articleService;
    }
    @GetMapping(value = "/article/image",produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public byte[] getPdfAttribution(@RequestParam("name") String name) throws Exception {
        return articleService.getImage(name);
    }

    @GetMapping("/run")
    public ResponseEntity<?> me(){
        return ResponseEntity.ok("server is running");
    }
}
