package far.insp.sirhat.persistance.repository;

import far.insp.sirhat.persistance.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
