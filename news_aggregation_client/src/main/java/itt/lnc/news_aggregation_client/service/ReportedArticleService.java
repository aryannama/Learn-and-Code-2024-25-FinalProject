package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.ReportedArticleApiClient;
import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.exception.ArticlesNotPresentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportedArticleService {
    private final ReportedArticleApiClient reportedArticleApiClient;

    public List<ArticleDto> getAllReportedArticles() {
        List<ArticleDto> reportedArticles = reportedArticleApiClient.getAllReportedArticles();
        if (reportedArticles.isEmpty()) {
            throw new ArticlesNotPresentException("No Reported Articles Present");
        }

        return reportedArticles;
    }

    public void hideReportedArticle(Long articleId) {
        reportedArticleApiClient.hideReportedArticle(articleId);
    }
}
