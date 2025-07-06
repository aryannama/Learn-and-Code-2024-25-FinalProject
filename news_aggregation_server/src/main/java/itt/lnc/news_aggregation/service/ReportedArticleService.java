package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;

import java.util.List;

public interface ReportedArticleService {
    List<ArticleDto> getAllReportedArticles();
    void hideReportedArticle(Long articleId);

}
