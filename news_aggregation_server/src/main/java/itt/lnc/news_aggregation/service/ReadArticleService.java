package itt.lnc.news_aggregation.service;

public interface ReadArticleService {
    void markAsRead(Long userId, Long articleId);
}