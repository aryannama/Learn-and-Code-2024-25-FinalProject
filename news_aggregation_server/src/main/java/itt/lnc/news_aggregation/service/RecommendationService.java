package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.Article;

import java.util.List;

public interface RecommendationService {
    List<Article> getRecommendedArticlesForUser(Long userId, List<Article> articles);
} 