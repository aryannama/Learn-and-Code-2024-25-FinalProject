package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ReactionsResponse;
import itt.lnc.news_aggregation.model.UserReaction;

public interface ReactionService {
    void toggleLike(Long userId, Long articleId);
    void toggleDislike(Long userId, Long articleId);
    void toggleReport(Long userId, Long articleId);
    ReactionsResponse getUserReaction(Long userId, Long articleId);
}
