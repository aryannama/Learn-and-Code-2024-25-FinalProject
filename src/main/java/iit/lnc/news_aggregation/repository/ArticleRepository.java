package iit.lnc.news_aggregation.repository;

import iit.lnc.news_aggregation.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
