package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.exception.ArticleNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.service.ReportedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReportedArticleService implements ReportedArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleDto> getAllReportedArticles() {
        List<Article> reportedArticles = articleRepository.findByReportCountGreaterThan(0);
        return reportedArticles.stream().map(articleMapper::toDto).toList();
    }

    @Override
    public void hideReportedArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        article.setHidden(true);
        articleRepository.save(article);
    }
}
