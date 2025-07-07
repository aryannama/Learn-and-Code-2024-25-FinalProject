package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.ArticleApiClient;
import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.dto.ArticleFilterRequest;
import itt.lnc.news_aggregation_client.dto.PaginatedResponse;
import itt.lnc.news_aggregation_client.dto.UserReaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleApiClient articleApiClient;

    public PaginatedResponse<ArticleDto> getArticles(ArticleFilterRequest filter, int page, int size) {
        validateFilter(filter);
        return articleApiClient.getAllArticles(filter, page, size);
    }

    private void validateFilter(ArticleFilterRequest filter) {
        if (filter.getStartDate() != null && filter.getEndDate() != null &&
                filter.getStartDate().isAfter(filter.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public ArticleDto getArticleById(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        return articleApiClient.getArticle(articleId);
    }

    public void markArticleAsRead(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        articleApiClient.markAsRead(articleId);
    }

    public UserReaction getUserReaction(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        return articleApiClient.getUserReactions(articleId);
    }

    public void likeArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        articleApiClient.toggleLike(articleId);
    }

    public void dislikeArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        articleApiClient.toggleDislike(articleId);
    }

    public void reportArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        articleApiClient.toggleReport(articleId);
    }

    public void saveArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("Invalid article ID");
        }
        articleApiClient.toggleSave(articleId);
    }

    public List<ArticleDto> getSavedArticles() {
        return articleApiClient.getSavedArticles();
    }

}
