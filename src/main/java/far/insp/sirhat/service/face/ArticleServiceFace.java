package far.insp.sirhat.service.face;

import far.insp.sirhat.persistance.entity.Article;

import java.util.List;

public interface ArticleServiceFace {
    Article findArticleById(String code);
    byte[] getImage(String name) throws Exception;
}
