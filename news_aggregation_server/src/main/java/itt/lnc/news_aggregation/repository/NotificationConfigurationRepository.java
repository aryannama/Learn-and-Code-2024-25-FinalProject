package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.NotificationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationConfigurationRepository extends JpaRepository<NotificationConfiguration, Long> {
    List<NotificationConfiguration> findByUserIdAndEnabledTrue(Long userId);
}
