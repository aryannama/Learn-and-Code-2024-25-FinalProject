package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.BlockedKeywordDto;
import itt.lnc.news_aggregation_client.exception.InvalidRequestException;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BlockedKeywordApiClient {

    public List<BlockedKeywordDto> getBlockedKeywords() {
        String accessToken = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.get(Urls.BLOCKED_KEYWORDS_URL, accessToken);

        if (response.statusCode() == HttpStatus.OK.value()) {
            return JsonParser.parseList(response.body(), BlockedKeywordDto.class);
        } else {
            throw new InvalidRequestException("Failed to fetch blocked keywords: " + response.body());
        }
    }

    public void addBlockedKeyword(String keyword) {
        String accessToken = SessionManager.getAccessToken();

        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = Urls.BLOCKED_KEYWORDS_URL + "?keyword=" + encodedKeyword;

        HttpResponse<String> response = APIClient.post(url, accessToken);

        if (response.statusCode() != HttpStatus.CREATED.value()) {
            throw new InvalidRequestException("Failed to add blocked keyword: " + response.body());
        }
    }

    public void removeBlockedKeyword(Long id) {
        String accessToken = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.delete(String.format(Urls.BLOCKED_KEYWORD_BY_ID_URL, id), accessToken);

        if (response.statusCode() != HttpStatus.NO_CONTENT.value()) {
            throw new InvalidRequestException("Failed to remove blocked keyword: " + response.body());
        }
    }
}
