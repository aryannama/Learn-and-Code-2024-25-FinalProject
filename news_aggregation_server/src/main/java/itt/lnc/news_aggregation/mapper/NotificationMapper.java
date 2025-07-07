package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.NotificationDto;
import itt.lnc.news_aggregation.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .articleId(notification.getArticle().getId())
                .articleTitle(notification.getArticle().getTitle())
                .timestamp(notification.getTimestamp())
                .build();
    }
}
