package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.NotificationDto;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    void notifyUsers(List<Article> articles);
    PaginatedResponse<NotificationDto> getNotificationsForUser(Long userId, Pageable pageable);
    void markAsRead(Long notificationId);
}
