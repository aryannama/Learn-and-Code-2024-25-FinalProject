package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.NotificationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationConfigurationRepository extends JpaRepository<NotificationConfiguration, Long> {
    List<NotificationConfiguration> findByUserIdAndEnabledTrue(Long userId);

    List<NotificationConfiguration> findByUserId(Long userId);

    Optional<NotificationConfiguration> findByUserIdAndCategoryId(Long userId, Long categoryId);

    boolean existsByUserIdAndCategoryId(Long userId, Long categoryId);
}
