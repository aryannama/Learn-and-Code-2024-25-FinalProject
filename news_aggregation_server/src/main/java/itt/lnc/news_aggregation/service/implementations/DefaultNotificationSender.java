package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationRepository;
import itt.lnc.news_aggregation.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultNotificationSender implements NotificationSender {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(User user, List<Article> articles) {
        log.info("Sending notification to user: {} ", user.getEmail());
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
        log.info("Notification has been sent to {}", user.getEmail());
    }
}
