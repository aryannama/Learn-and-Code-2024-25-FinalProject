package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation.model.NotificationConfiguration;
import org.springframework.stereotype.Component;

@Component
public class NotificationConfigurationMapper {
    public NotificationConfigurationDto toDto(NotificationConfiguration notificationConfiguration) {
        return NotificationConfigurationDto.builder()
                .id(notificationConfiguration.getId())
                .categoryId(notificationConfiguration.getCategory().getId())
                .categoryName(notificationConfiguration.getCategory().getName())
                .enabled(notificationConfiguration.isEnabled())
                .keywords(notificationConfiguration.getKeywords())
                .build();
    }
}
