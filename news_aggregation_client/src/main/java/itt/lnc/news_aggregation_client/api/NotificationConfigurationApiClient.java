package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import itt.lnc.news_aggregation_client.utils.UrlBuilder;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class NotificationConfigurationApiClient {

    public List<NotificationConfigurationDto> getAllNotificationConfigurations() {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(Urls.NOTIFICATION_CONFIRMATION_URL, token);
        return JsonParser.parseList(response.body(), NotificationConfigurationDto.class);
    }

    public void toggleCategory(Long categoryId, boolean enabled) {
        String token = SessionManager.getAccessToken();
        String url = UrlBuilder.buildUrlWithParams(String.format(Urls.NOTIFICATION_CONFIGURATION_CATEGORY, categoryId), Map.of("enabled", String.valueOf(enabled)));
        APIClient.post(url, token);
    }

    public void addKeyword(Long categoryId, String keyword) {
        String token = SessionManager.getAccessToken();
        String url = UrlBuilder.buildUrlWithParams(String.format(Urls.NOTIFICATION_CONFIGURATION_KEYWORD, categoryId), Map.of("keyword", keyword));
        APIClient.post(url, token);
    }

    public void removeKeyword(Long categoryId, String keyword) {
        String token = SessionManager.getAccessToken();
        String url = UrlBuilder.buildUrlWithParams(String.format(Urls.NOTIFICATION_CONFIGURATION_KEYWORD, categoryId), Map.of("keyword", keyword));
        APIClient.delete(url, token);
    }
}
