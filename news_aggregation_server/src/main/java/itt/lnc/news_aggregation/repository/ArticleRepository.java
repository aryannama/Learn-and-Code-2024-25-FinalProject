package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
