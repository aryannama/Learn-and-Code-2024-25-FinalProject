package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.User;

import java.util.List;

public interface NotificationSender {
    void sendNotification(User user, List<Article> article);
}
