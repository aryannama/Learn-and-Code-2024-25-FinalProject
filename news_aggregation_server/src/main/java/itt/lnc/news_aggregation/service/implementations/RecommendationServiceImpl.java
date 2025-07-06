package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.helper.UserPreferenceHelper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final UserPreferenceHelper userPreferenceHelper;

    @Override
    public List<Article> getRecommendedArticlesForUser(Long userId, List<Article> articles) {
        log.info("Getting recommended articles for user {}", userId);
        Set<Long> categoryIds = userPreferenceHelper.getPreferredCategories(userId);
        Set<String> keywords = userPreferenceHelper.getPreferredKeywords(userId);

        List<Article> recommendedArticles = articles.stream()
                .filter(article -> isArticleRelevant(article, categoryIds, keywords))
                .toList();
        log.info("Found {} recommended articles for user Id: {}", recommendedArticles.size(), userId);
        return recommendedArticles;
    }

    private boolean isArticleRelevant(Article article, Set<Long> categoryIds, Set<String> keywords) {
        boolean matchesCategory = article.getCategories().stream()
                .anyMatch(category -> categoryIds.contains(category.getId()));

        boolean matchesKeywords = keywords.stream().anyMatch(keyword ->
                containsIgnoreCase(article.getTitle(), keyword) ||
                        containsIgnoreCase(article.getDescription(), keyword)
        );

        return matchesCategory || matchesKeywords;
    }

    private boolean containsIgnoreCase(String text, String keyword) {
        return text != null && text.toLowerCase().contains(keyword.toLowerCase());
    }

} 