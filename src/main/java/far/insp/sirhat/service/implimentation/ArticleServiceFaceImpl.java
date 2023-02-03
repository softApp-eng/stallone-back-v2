package far.insp.sirhat.service.implimentation;

import far.insp.sirhat.exception.BadRequestException;
import far.insp.sirhat.persistance.entity.Article;
import far.insp.sirhat.persistance.repository.ArticleRepository;
import far.insp.sirhat.service.face.ArticleServiceFace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
public class ArticleServiceFaceImpl implements ArticleServiceFace {

    private final ArticleRepository articleRepository;

    @Value("${stallone.images}")
    private String articleFolder;

    public ArticleServiceFaceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article findArticleById(String code) {
        return articleRepository.findFirstByCode(code)
                .orElseThrow(()->new BadRequestException("Article introuvable"));
    }

    @Override
    public byte[] getImage(String name) throws Exception {
        File file = new File(articleFolder+"/"+name);
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }
}
