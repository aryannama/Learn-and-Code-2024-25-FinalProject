package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUserIdAndReadFalseOrderByTimestampDesc(Long userId, Pageable pageable);
    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}
