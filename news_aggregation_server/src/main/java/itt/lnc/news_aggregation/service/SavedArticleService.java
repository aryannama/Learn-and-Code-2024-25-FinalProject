package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDTO;

import java.util.List;

public interface SavedArticleService {
    void toggleSave(Long userId, Long articleId);
    List<ArticleDTO> getSavedArticles(Long userId);
}
