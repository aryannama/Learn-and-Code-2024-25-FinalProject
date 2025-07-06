package itt.lnc.news_aggregation_client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.dto.ArticleFilterRequest;
import itt.lnc.news_aggregation_client.dto.PaginatedResponse;
import itt.lnc.news_aggregation_client.dto.UserReaction;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import itt.lnc.news_aggregation_client.utils.UrlBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class ArticleApiClient {

    public PaginatedResponse<ArticleDto> getAllArticles(ArticleFilterRequest filter, int page, int size) {
        String token = SessionManager.getAccessToken();
        Map<String, String> queryParams = filter.toQueryParams();
        queryParams.put("page", String.valueOf(page));
        queryParams.put("size", String.valueOf(size));

        String url = UrlBuilder.buildUrlWithParams(Urls.ARTICLES_URL, queryParams);
        HttpResponse<String> response = APIClient.get(url, token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to fetch articles: " + response.body());
        }

        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }

    public ArticleDto getArticle(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.get(String.format(Urls.ARTICLE_BY_ID_URL, articleId), token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to fetch article: " + response.body());
        }

        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }

    public void markAsRead(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.put(String.format(Urls.READ_ARTICLE_URL, articleId), token);

        if (response.statusCode() != HttpStatus.NO_CONTENT.value()) {
            throw new RuntimeException("Failed to mark article as read: " + response.body());
        }
    }

    public UserReaction getUserReactions(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.get(String.format(Urls.REACTIONS_URL, articleId), token);
        if (response.statusCode() == HttpStatus.OK.value()) {
            return JsonParser.parse(response.body(), new TypeReference<>() {
            });
        }
        return new UserReaction();
    }

    public void toggleSave(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.post(String.format(Urls.SAVE_ARTICLE_URL, articleId), token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to toggle save article: " + response.body());
        }
    }

    public void toggleLike(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.post(String.format(Urls.LIKE_ARTICLE_URL, articleId), token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to toggle like article: " + response.body());
        }
    }

    public void toggleDislike(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.post(String.format(Urls.DISLIKE_ARTICLE_URL, articleId), token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to toggle dislike article: " + response.body());
        }
    }

    public void toggleReport(Long articleId) {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.post(String.format(Urls.REPORT_ARTICLE_URL, articleId), token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to toggle report article: " + response.body());
        }
    }

    public List<ArticleDto> getSavedArticles() {
        String token = SessionManager.getAccessToken();

        HttpResponse<String> response = APIClient.get(Urls.SAVED_ARTICLES_URL, token);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException("Failed to fetch saved articles: " + response.body());
        }

        return JsonParser.parseList(response.body(), ArticleDto.class);
    }
}
