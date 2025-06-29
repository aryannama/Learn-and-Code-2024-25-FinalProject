package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.model.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    List<Article> saveArticles(List<ArticleDTO> articles);
    void saveArticle(ArticleDTO articleDTO);
    PaginatedResponse<ArticleDTO> getAllArticles(ArticleFilter articleFilter, Pageable pageable);
    ArticleDTO getArticle(Long id);
    Article getArticleById(Long id);
    void deleteArticle(Long id);
}
