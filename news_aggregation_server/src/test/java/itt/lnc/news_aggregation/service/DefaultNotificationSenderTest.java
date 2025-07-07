package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultNotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultNotificationSenderTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private DefaultNotificationSender notificationSender;

    private User user;
    private Article firstArticle;
    private Article secondArticle;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).email("user@example.com").build();

        firstArticle = Article.builder().id(101L).build();
        secondArticle = Article.builder().id(102L).build();
    }

    @Test
    void testSendNotification_savesNewNotifications() {
        when(notificationRepository.existsByUserIdAndArticleId(user.getId(), firstArticle.getId())).thenReturn(false);
        when(notificationRepository.existsByUserIdAndArticleId(user.getId(), secondArticle.getId())).thenReturn(false);

        notificationSender.sendNotification(user, List.of(firstArticle, secondArticle));

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(2)).save(captor.capture());

        List<Notification> savedNotifications = captor.getAllValues();

        // Validate the saved notifications
        for (Notification notif : savedNotifications) {
            assert notif.getUser().equals(user);
            assert !notif.isRead();
            assert notif.getArticle().getId().equals(firstArticle.getId()) ||
                    notif.getArticle().getId().equals(secondArticle.getId());
        }
    }

    @Test
    void testSendNotification_skipsExistingNotifications() {
        when(notificationRepository.existsByUserIdAndArticleId(user.getId(), firstArticle.getId())).thenReturn(true);
        when(notificationRepository.existsByUserIdAndArticleId(user.getId(), secondArticle.getId())).thenReturn(false);

        notificationSender.sendNotification(user, List.of(firstArticle, secondArticle));

        verify(notificationRepository, times(1)).save(any(Notification.class)); // Only for secondArticle
        verify(notificationRepository, times(2)).existsByUserIdAndArticleId(eq(user.getId()), anyLong());
    }
}
