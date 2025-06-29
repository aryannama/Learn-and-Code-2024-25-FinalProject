package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.exception.ArticleNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.specification.ArticleSpecification;
import itt.lnc.news_aggregation.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultArticleService implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public List<Article> saveArticles(List<ArticleDTO> articles) {
        List<Article> entities = articles.stream()
                .map(articleMapper::toEntity)
                .toList();
        return articleRepository.saveAll(entities);
    }

    public void saveArticle(ArticleDTO articleDTO) {
        Article article = articleMapper.toEntity(articleDTO);
        articleRepository.save(article);
    }

    public PaginatedResponse<ArticleDTO> getAllArticles(ArticleFilter articleFilter, Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(
                ArticleSpecification.hasCategory(articleFilter.getCategory())
                        .and(ArticleSpecification.dateBetween(articleFilter.getStartDate(), articleFilter.getEndDate()))
                        .and(ArticleSpecification.searchQuery(articleFilter.getQuery()))
                        .and(ArticleSpecification.isNotHidden()), pageable);

        return PaginatedResponse.<ArticleDTO>builder().currentPage(articles.getNumber())
                .pageSize(articles.getSize())
                .totalElements(articles.getTotalElements())
                .totalPages(articles.getTotalPages())
                .content(articles.getContent().stream().map(articleMapper::toDto).toList())
                .build();
    }

    public ArticleDTO getArticle(Long id) {
        Article article = getArticleById(id);
        return articleMapper.toDto(article);
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
