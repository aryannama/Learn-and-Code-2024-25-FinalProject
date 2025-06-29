package itt.lnc.news_aggregation.service;

public interface ReactionService {
    void toggleLike(Long userId, Long articleId);
    void toggleDislike(Long userId, Long articleId);
    void toggleReport(Long userId, Long articleId);
}
