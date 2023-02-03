package far.insp.sirhat.persistance.repository;

import far.insp.sirhat.persistance.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Optional<Article> findFirstByCode(String code);
}
