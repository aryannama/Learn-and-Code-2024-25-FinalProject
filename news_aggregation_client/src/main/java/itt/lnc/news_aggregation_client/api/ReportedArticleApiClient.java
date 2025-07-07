package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ReportedArticleApiClient {
    public List<ArticleDto> getAllReportedArticles() {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(Urls.REPORTED_ARTICLES_URL, token);
        return JsonParser.parseList(response.body(), ArticleDto.class);
    }

    public void hideReportedArticle(Long articleId) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.patch(String.format(Urls.HIDE_REPORTED_ARTICLE_URL, articleId), token);
    }
}
