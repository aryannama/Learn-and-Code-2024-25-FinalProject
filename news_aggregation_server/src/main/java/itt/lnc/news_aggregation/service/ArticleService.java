package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.model.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    List<Article> saveArticles(List<ArticleDto> articles);
    void saveArticle(ArticleDto articleDTO);
    PaginatedResponse<ArticleDto> getAllArticles(ArticleFilter articleFilter, Pageable pageable);
    ArticleDto getArticle(Long id);
    Article getArticleById(Long id);
    void deleteArticle(Long id);
}
