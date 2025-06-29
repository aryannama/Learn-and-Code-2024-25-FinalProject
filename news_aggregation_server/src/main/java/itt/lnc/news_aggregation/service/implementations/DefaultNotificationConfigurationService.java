package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.model.NotificationConfiguration;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationConfigurationRepository;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.service.NotificationConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultNotificationConfigurationService implements NotificationConfigurationService {

    private final NotificationConfigurationRepository notificationConfigurationRepository;
    private final CategoryService categoryService;

    @Override
    public List<Article> getMatchingArticles(Long userId, List<Article> articles) {
        List<NotificationConfiguration> notificationConfigurations = notificationConfigurationRepository.findByUserIdAndEnabledTrue(userId);
        List<Article> matchingArticles = new ArrayList<>();

        for (Article article : articles) {
            for (NotificationConfiguration configuration : notificationConfigurations) {
                if (hasMatchingCategory(article, configuration.getCategory())) {
                    Set<String> keywords = configuration.getKeywords();
                    if (keywords.isEmpty() || matchesAnyKeyword(article, keywords)) {
                        matchingArticles.add(article);
                        break;
                    }
                }
            }
        }
        return matchingArticles;
    }

    @Override
    public void createDefaultNotificationConfigurations(User user) {
        List<Category> categories = categoryService.getAllCategories();
        List<NotificationConfiguration> configurations = new ArrayList<>();

        for (Category category : categories) {
            NotificationConfiguration configuration = NotificationConfiguration.builder()
                    .user(user)
                    .category(category)
                    .enabled(true)
                    .build();
            configurations.add(configuration);
        }
        notificationConfigurationRepository.saveAll(configurations);
    }

    private boolean hasMatchingCategory(Article article, Category configCategory) {
        for (Category articleCategory : article.getCategories()) {
            if (articleCategory.getName().equalsIgnoreCase(configCategory.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesAnyKeyword(Article article, Set<String> keywords) {
        String title = article.getTitle() != null ? article.getTitle().toLowerCase() : "";
        String description = article.getDescription() != null ? article.getDescription().toLowerCase() : "";

        for (String keyword : keywords) {
            if (title.contains(keyword.toLowerCase()) || description.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
