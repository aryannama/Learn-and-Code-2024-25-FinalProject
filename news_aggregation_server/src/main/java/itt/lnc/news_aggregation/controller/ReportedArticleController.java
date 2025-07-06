package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.service.ReportedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reported-articles")
@RequiredArgsConstructor
public class ReportedArticleController {

    private final ReportedArticleService reportedArticleService;

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllReportedArticles() {
        return ResponseEntity.ok(reportedArticleService.getAllReportedArticles());
    }

    @PatchMapping("/{articleId}/hide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideArticle(@PathVariable("articleId") Long articleId) {
        reportedArticleService.hideReportedArticle(articleId);
    }
}
