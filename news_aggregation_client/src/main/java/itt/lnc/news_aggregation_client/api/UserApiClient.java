package itt.lnc.news_aggregation_client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.dto.UserDetailsDto;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

import static itt.lnc.news_aggregation_client.constants.Urls.CURRENT_USER_URL;

@Component
public class UserApiClient {

    public UserDetailsDto getCurrentUser() {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.get(CURRENT_USER_URL, token);
        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }
}
