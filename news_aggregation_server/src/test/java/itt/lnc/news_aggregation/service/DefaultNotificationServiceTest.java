package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.NotificationDto;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.mapper.NotificationMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Notification;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultNotificationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationConfigurationService notificationConfigurationService;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationMapper notificationMapper;
    @Mock
    private NotificationSender firstSender;
    @Mock
    private NotificationSender secondSender;

    @InjectMocks
    private DefaultNotificationService notificationService;

    private User user;
    private Article article;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).email("test@example.com").build();
        article = Article.builder().id(101L).title("News").build();
    }

    @Test
    void testNotifyUsers_sendsNotificationToMatchingUsers() {
        List<User> users = List.of(user);
        List<Article> newArticles = List.of(article);

        when(userRepository.findAll()).thenReturn(users);
        when(notificationConfigurationService.getMatchingArticles(user.getId(), newArticles))
                .thenReturn(newArticles);

        notificationService = new DefaultNotificationService(
                userRepository,
                List.of(firstSender, secondSender),
                notificationConfigurationService,
                notificationRepository,
                notificationMapper
        );

        notificationService.notifyUsers(newArticles);

        verify(firstSender).sendNotification(user, newArticles);
        verify(secondSender).sendNotification(user, newArticles);
    }

    @Test
    void testNotifyUsers_skipsIfNoMatches() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(notificationConfigurationService.getMatchingArticles(user.getId(), List.of(article)))
                .thenReturn(Collections.emptyList());

        notificationService = new DefaultNotificationService(
                userRepository,
                List.of(firstSender),
                notificationConfigurationService,
                notificationRepository,
                notificationMapper
        );

        notificationService.notifyUsers(List.of(article));

        verify(firstSender, never()).sendNotification(any(), any());
    }

    @Test
    void testGetNotificationsForUser_returnsPaginatedAndMarksAsRead() {
        Notification firstNotification = Notification.builder().id(1L).read(false).build();
        Notification secondNotification = Notification.builder().id(2L).read(false).build();
        Page<Notification> page = new PageImpl<>(List.of(firstNotification, secondNotification));
        Pageable pageable = PageRequest.of(0, 10);

        NotificationDto dto1 = NotificationDto.builder()
                .id(1L)
                .articleTitle("Title 1")
                .build();

        NotificationDto dto2 = NotificationDto.builder()
                .id(2L)
                .articleTitle("Title 2")
                .build();

        when(notificationRepository.findByUserIdAndReadFalseOrderByTimestampDesc(1L, pageable)).thenReturn(page);
        when(notificationMapper.toDto(firstNotification)).thenReturn(dto1);
        when(notificationMapper.toDto(secondNotification)).thenReturn(dto2);

        PaginatedResponse<NotificationDto> result = notificationService.getNotificationsForUser(1L, pageable);

        assertEquals(2, result.getContent().size());
        assertTrue(firstNotification.isRead());
        assertTrue(secondNotification.isRead());
        verify(notificationRepository).saveAll(List.of(firstNotification, secondNotification));
    }

    @Test
    void testMarkAsRead_shouldUpdateIfNotAlreadyRead() {
        Notification notification = Notification.builder().id(1L).read(false).build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        notificationService.markAsRead(1L);

        assertTrue(notification.isRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void testMarkAsRead_shouldSkipIfAlreadyRead() {
        Notification notification = Notification.builder().id(1L).read(true).build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        notificationService.markAsRead(1L);

        verify(notificationRepository, never()).save(notification);
    }

    @Test
    void testMarkAsRead_shouldThrowIfNotFound() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> notificationService.markAsRead(1L));

        assertEquals("Notification not found: 1", ex.getMessage());
    }
}
