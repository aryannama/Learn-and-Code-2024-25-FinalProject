package itt.lnc.news_aggregation_client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.dto.ExternalServerDto;
import itt.lnc.news_aggregation_client.dto.UpdateExternalServerDto;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

import static itt.lnc.news_aggregation_client.constants.Urls.EXTERNAL_SERVER_BY_ID_URL;
import static itt.lnc.news_aggregation_client.constants.Urls.EXTERNAL_SERVER_URL;

@Component
public class ExternalServerApiClient {
    public List<ExternalServerDto> getAllServers() {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(EXTERNAL_SERVER_URL, token);
        return JsonParser.parseList(response.body(), ExternalServerDto.class);
    }

    public ExternalServerDto getServerById(Long id) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(String.format(EXTERNAL_SERVER_BY_ID_URL, id), token);
        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }

    public void updateServerApiKey(Long id, UpdateExternalServerDto request) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.patch(String.format(EXTERNAL_SERVER_BY_ID_URL, id), JsonParser.toJson(request), token);

        if (response.statusCode() == HttpStatus.NO_CONTENT.value()) {
            ConsoleUtil.printMessage("Successfully updated external server Api Key");
        }
    }
}
