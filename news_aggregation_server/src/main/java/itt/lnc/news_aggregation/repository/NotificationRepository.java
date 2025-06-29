package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
