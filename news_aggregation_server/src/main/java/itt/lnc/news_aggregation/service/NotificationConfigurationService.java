package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.User;

import java.util.List;

public interface NotificationConfigurationService {
    List<Article> getMatchingArticles(Long userId, List<Article> articles);

    void createDefaultNotificationConfigurations(User user);

    List<NotificationConfigurationDto> getUserNotificationConfiguration(Long userId);

    void toggleCategory(Long userId, Long categoryId, boolean enabled);

    void addKeyword(Long userId, Long categoryId, String keyword);

    void removeKeyword(Long userId, Long categoryId, String keyword);
}
