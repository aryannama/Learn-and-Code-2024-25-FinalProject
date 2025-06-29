package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationRepository;
import itt.lnc.news_aggregation.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultNotificationSender implements NotificationSender {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(User user, List<Article> articles) {

        for (Article article : articles) {
            if (!notificationRepository.existsByUserIdAndArticleId(user.getId(), article.getId())) {
                Notification notification = Notification.builder()
                        .user(user)
                        .article(article)
                        .read(false)
                        .build();
                notificationRepository.save(notification);
            }
        }
    }
}
