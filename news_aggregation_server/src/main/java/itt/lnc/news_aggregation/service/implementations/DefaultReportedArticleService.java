package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.service.ReportedArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultReportedArticleService implements ReportedArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleDto> getAllReportedArticles() {
        log.info("Getting all reported articles");
        List<Article> reportedArticles = articleRepository.findByReportCountGreaterThan(0);
        if (reportedArticles.isEmpty()) {
            throw new ResourceNotFoundException("No reported articles found");
        }
        log.info("{} reported articles found", reportedArticles.size());
        return reportedArticles.stream().map(articleMapper::toDto).toList();
    }

    @Override
    public void hideReportedArticle(Long articleId) {
        log.info("Hiding reported article {}", articleId);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with ID: " + articleId));

        article.setHidden(true);
        articleRepository.save(article);
        log.info("{} reported article hidden", articleId);
    }
}
