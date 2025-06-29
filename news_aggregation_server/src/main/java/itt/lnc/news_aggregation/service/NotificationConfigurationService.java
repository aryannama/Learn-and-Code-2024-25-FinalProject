package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.User;

import java.util.List;

public interface NotificationConfigurationService {
    List<Article> getMatchingArticles(Long userId, List<Article> articles);
    void createDefaultNotificationConfigurations(User user);
}
