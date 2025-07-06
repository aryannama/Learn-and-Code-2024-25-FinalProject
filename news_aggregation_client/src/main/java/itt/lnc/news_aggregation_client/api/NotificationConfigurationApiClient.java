package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation_client.exception.InvalidRequestException;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class NotificationConfigurationApiClient {

    public List<NotificationConfigurationDto> getAllNotificationConfigurations() {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(Urls.NOTIFICATION_CONFIRMATION_URL, token);
        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new InvalidRequestException("Failed to fetch notification configurations: " + response.body());
        }

        return JsonParser.parseList(response.body(), NotificationConfigurationDto.class);
    }

    public void toggleCategory(Long categoryId, boolean enabled) {
        String token = SessionManager.getAccessToken();
        String url = String.format(Urls.NOTIFICATION_CONFIGURATION_CATEGORY, categoryId) + "?enabled=" + enabled;
        HttpResponse<String> response = APIClient.post(url, token);
        if (response.statusCode() != HttpStatus.NO_CONTENT.value()) {
            throw new InvalidRequestException("Failed to toggle notification category: " + response.body());
        }
    }

    public void addKeyword(Long categoryId, String keyword) {
        String token = SessionManager.getAccessToken();
        String url = String.format(Urls.NOTIFICATION_CONFIGURATION_KEYWORD, categoryId) + "?keyword=" + keyword;
        HttpResponse<String> response = APIClient.post(url, token);
        if (response.statusCode() != HttpStatus.NO_CONTENT.value()) {
            throw new InvalidRequestException("Failed to add notification keyword: " + response.body());
        }
    }

    public void removeKeyword(Long categoryId, String keyword) {
        String token = SessionManager.getAccessToken();
        String url = String.format(Urls.NOTIFICATION_CONFIGURATION_KEYWORD, categoryId) + "?keyword=" + keyword;
        HttpResponse<String> response = APIClient.delete(url, token);
        if (response.statusCode() != HttpStatus.NO_CONTENT.value()) {
            throw new InvalidRequestException("Failed to remove notification keyword: " + response.body());
        }
    }

}
