package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.NotificationApiClient;
import itt.lnc.news_aggregation_client.dto.NotificationDto;
import itt.lnc.news_aggregation_client.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationApiClient notificationApiClient;

    public PaginatedResponse<NotificationDto> getNotifications(int page, int size) {
        return notificationApiClient.getAllNotifications(page, size);
    }
}
