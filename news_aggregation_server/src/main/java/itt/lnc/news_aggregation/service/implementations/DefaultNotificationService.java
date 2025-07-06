package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.NotificationDto;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.mapper.NotificationMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.NotificationConfigurationService;
import itt.lnc.news_aggregation.service.NotificationSender;
import itt.lnc.news_aggregation.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

    private final UserRepository userRepository;
    private final List<NotificationSender> notificationSenders;
    private final NotificationConfigurationService notificationConfigurationService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void notifyUsers(List<Article> articles) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Article> matchedArticles = notificationConfigurationService.getMatchingArticles(user.getId(), articles);
            if (!matchedArticles.isEmpty()) {
                for (NotificationSender sender : notificationSenders) {
                    sender.sendNotification(user, matchedArticles);
                }
            }
        }
    }

    @Override
    public PaginatedResponse<NotificationDto> getNotificationsForUser(Long userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndReadFalseOrderByTimestampDesc(userId, pageable);

        List<Notification> readArticles = notifications.getContent().stream()
                .peek(n -> n.setRead(true))
                .toList();
        notificationRepository.saveAll(readArticles);

        return new PaginatedResponse<>(notifications, notifications.getContent().stream()
                .map(notificationMapper::toDto)
                .toList());
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + notificationId));

        if (!notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
