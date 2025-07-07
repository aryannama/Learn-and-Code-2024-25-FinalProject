package itt.lnc.news_aggregation_client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.NotificationDto;
import itt.lnc.news_aggregation_client.dto.PaginatedResponse;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import itt.lnc.news_aggregation_client.utils.UrlBuilder;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationApiClient {

    public PaginatedResponse<NotificationDto> getAllNotifications(int page, int size) {
        String token = SessionManager.getAccessToken();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page", String.valueOf(page));
        queryParams.put("size", String.valueOf(size));
        String url = UrlBuilder.buildUrlWithParams(Urls.NOTIFICATIONS_URL, queryParams);
        HttpResponse<String> response = APIClient.get(url, token);

        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }
}
