package far.insp.sirhat.service.face;

import far.insp.sirhat.persistance.entity.Article;
import far.insp.sirhat.presentation.dto.QuantiteArticleDto;

import java.util.List;

public interface ArticleServiceFace {
    Article findArticleByCode(String code);
    Article addQuantiteArticleById(QuantiteArticleDto quantiteArticleDto);
    List<Article> stock();
    byte[] getImage(String name) throws Exception;
}
