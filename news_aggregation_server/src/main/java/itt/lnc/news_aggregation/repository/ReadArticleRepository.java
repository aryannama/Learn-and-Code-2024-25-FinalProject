package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.ReadArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadArticleRepository extends JpaRepository<ReadArticle, Long> {
    List<ReadArticle> findByUserId(Long userId);
    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
