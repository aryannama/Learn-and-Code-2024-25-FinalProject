package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.service.ReportedArticleService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportedArticleHandler {
    private final ReportedArticleService reportedArticleService;

    public void listReportedArticles() {
        List<ArticleDto> reportedArticles = reportedArticleService.getAllReportedArticles();
        ConsoleUtil.printMessage("Reported Articles:");
        ConsoleUtil.displayList(reportedArticles, articleDto -> String.format("[%s] %s", articleDto.getId(), articleDto.getTitle()));
    }

    public void hideReportedArticle() {
        Long articleId = (long) ConsoleUtil.readInt("Enter the ID of the article to hide: ");
        reportedArticleService.hideReportedArticle(articleId);
        ConsoleUtil.printMessage("Article with ID " + articleId + " has been hidden.");
    }
}
