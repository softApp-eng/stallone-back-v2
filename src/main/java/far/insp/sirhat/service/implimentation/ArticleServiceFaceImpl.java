package far.insp.sirhat.service.implimentation;

import far.insp.sirhat.exception.BadRequestException;
import far.insp.sirhat.persistance.entity.Article;
import far.insp.sirhat.persistance.repository.ArticleRepository;
import far.insp.sirhat.service.face.ArticleServiceFace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceFaceImpl implements ArticleServiceFace {

    private final ArticleRepository articleRepository;

    public ArticleServiceFaceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(()->new BadRequestException("Article introuvable"));
    }
}
