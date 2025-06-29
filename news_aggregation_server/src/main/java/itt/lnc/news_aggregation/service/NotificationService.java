package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;

import java.util.List;

public interface NotificationService {
    void notifyUsers(List<Article> articles);
    List<Notification> getNotificationsForUser(Long userId);
    void markAsRead(Long notificationId);
}
