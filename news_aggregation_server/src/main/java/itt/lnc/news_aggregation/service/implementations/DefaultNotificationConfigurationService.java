package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.NotificationConfigurationMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.model.NotificationConfiguration;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationConfigurationRepository;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.service.NotificationConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultNotificationConfigurationService implements NotificationConfigurationService {

    private final NotificationConfigurationRepository notificationConfigurationRepository;
    private final CategoryService categoryService;
    private final NotificationConfigurationMapper notificationConfigurationMapper;

    @Override
    public List<Article> getMatchingArticles(Long userId, List<Article> articles) {
        log.info("Fetching matching articles for user ID: {} to send notification", userId);
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
        log.info("Found {} matching articles for user ID: {}", matchingArticles.size(), userId);
        return matchingArticles;
    }

    @Override
    public void createDefaultNotificationConfigurations(User user) {
        log.info("Creating default notification configurations");
        List<Category> categories = categoryService.getAllCategories();
        List<NotificationConfiguration> configurations = new ArrayList<>();

        for (Category category : categories) {
            NotificationConfiguration configuration = NotificationConfiguration.builder()
                    .user(user)
                    .category(category)
                    .enabled(false)
                    .build();
            configurations.add(configuration);
        }
        log.info("Created {} notification configurations", configurations.size());
        notificationConfigurationRepository.saveAll(configurations);
    }

    @Override
    public List<NotificationConfigurationDto> getUserNotificationConfiguration(Long userId) {
        List<NotificationConfiguration> configurations = notificationConfigurationRepository.findByUserId(userId);
        return configurations.stream().map(notificationConfigurationMapper::toDto).toList();
    }

    @Override
    public void toggleCategory(Long userId, Long categoryId, boolean enabled) {
        log.info("Toggling notification configuration for user ID: {}, category ID: {}, enabled: {}", userId, categoryId, enabled);
        NotificationConfiguration configuration = getNotificationConfiguration(userId, categoryId);
        configuration.setEnabled(enabled);
        notificationConfigurationRepository.save(configuration);
        log.info("Notification configuration for user ID: {}, category ID: {} has been updated to enabled: {}", userId, categoryId, enabled);
    }

    @Override
    public void addKeyword(Long userId, Long categoryId, String keyword) {
        log.info("Adding keyword '{}' to notification configuration for user ID: {}, category ID: {}", keyword, userId, categoryId);
        NotificationConfiguration configuration = getNotificationConfiguration(userId, categoryId);
        configuration.getKeywords().add(keyword.toLowerCase());
        notificationConfigurationRepository.save(configuration);
        log.info("Keyword '{}' added to notification configuration for user ID: {}, category ID: {}", keyword, userId, categoryId);
    }

    @Override
    public void removeKeyword(Long userId, Long categoryId, String keyword) {
        log.info("Removing keyword '{}' from notification configuration for user ID: {}, category ID: {}", keyword, userId, categoryId);
        NotificationConfiguration configuration = getNotificationConfiguration(userId, categoryId);
        configuration.getKeywords().remove(keyword.toLowerCase());
        notificationConfigurationRepository.save(configuration);
        log.info("Removed keyword '{}' from notification configuration for user ID: {}, category ID: {}", keyword, userId, categoryId);
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

    private NotificationConfiguration getNotificationConfiguration(Long userId, Long categoryId) {
        return notificationConfigurationRepository.findByUserIdAndCategoryId(userId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification configuration not found for user and category"));
    }
}
