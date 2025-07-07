package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.BlockedKeywordDto;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
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

        return JsonParser.parseList(response.body(), BlockedKeywordDto.class);

    }

    public void addBlockedKeyword(String keyword) {
        String accessToken = SessionManager.getAccessToken();

        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = Urls.BLOCKED_KEYWORDS_URL + "?keyword=" + encodedKeyword;

        HttpResponse<String> response = APIClient.post(url, accessToken);
    }

    public void removeBlockedKeyword(Long id) {
        String accessToken = SessionManager.getAccessToken();
        APIClient.delete(String.format(Urls.BLOCKED_KEYWORD_BY_ID_URL, id), accessToken);
    }
}
