package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.SavedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends JpaRepository<SavedArticle,Long> {
    Optional<SavedArticle> findByUserIdAndArticleId(Long userId, Long articleId);
    List<SavedArticle> findAllByUserId(Long userId);
    void deleteByUserIdAndArticleId(Long userId, Long articleId);
}
