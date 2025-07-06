package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.exception.ArticleNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.specification.ArticleSpecification;
import itt.lnc.news_aggregation.security.SecurityContext;
import itt.lnc.news_aggregation.service.ArticleService;
import itt.lnc.news_aggregation.service.BlockedKeywordService;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.service.RecommendationService;
import itt.lnc.news_aggregation.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultArticleService implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CategoryService categoryService;
    private final BlockedKeywordService blockedKeywordService;
    private final RecommendationService recommendationService;

    @Override
    public List<Article> saveArticles(List<ArticleDto> articles) {
        List<Article> entities = articles.stream()
                .map(articleMapper::toEntity)
                .toList();
        return articleRepository.saveAll(entities);
    }

    @Override
    public void saveArticle(ArticleDto articleDTO) {
        Article article = articleMapper.toEntity(articleDTO);
        articleRepository.save(article);
    }

    @Override
    public ArticleDto getArticle(Long id) {
        Article article = getArticleById(id);
        return articleMapper.toDto(article);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<ArticleDto> getAllArticles(ArticleFilter articleFilter, Pageable pageable) {
        Long userId = SecurityContext.getCurrentUserId();

        List<Category> hiddenCategories = categoryService.getHiddenCategories();
        List<BlockedKeyword> blockedKeywords = blockedKeywordService.getAllBlockedKeywords();

        Specification<Article> spec = buildArticleSpecification(articleFilter, hiddenCategories, blockedKeywords);
        List<Article> filteredArticles = articleRepository.findAll(spec);

        List<Article> recommendedArticles = recommendationService.getRecommendedArticlesForUser(userId, filteredArticles);
        List<Article> remainingArticles = getRemainingArticles(filteredArticles, recommendedArticles);

        List<Article> combinedResults = new ArrayList<>();
        combinedResults.addAll(recommendedArticles);
        combinedResults.addAll(remainingArticles);

        List<Article> sortedArticles = sortArticles(combinedResults, pageable.getSort());
        List<Article> paginatedContent = PaginationUtil.paginate(sortedArticles, pageable);

        return PaginatedResponse.<ArticleDto>builder().currentPage(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalElements(combinedResults.size())
                .totalPages(PaginationUtil.calculateTotalPages(combinedResults.size(), pageable.getPageSize()))
                .content(paginatedContent.stream().map(articleMapper::toDto).toList())
                .build();
    }

    private List<Article> getRemainingArticles(List<Article> filtered, List<Article> recommended) {
        Set<Long> recommendedIds = recommended.stream().map(Article::getId).collect(Collectors.toSet());
        return filtered.stream()
                .filter(article -> !recommendedIds.contains(article.getId()))
                .toList();
    }

    private Specification<Article> buildArticleSpecification(ArticleFilter filter, List<Category> hiddenCategories, List<BlockedKeyword> blockedKeywords) {
        return ArticleSpecification.hasCategory(filter.getCategory())
                .and(ArticleSpecification.dateBetween(filter.getStartDate(), filter.getEndDate()))
                .and(ArticleSpecification.searchQuery(filter.getQuery()))
                .and(ArticleSpecification.isNotHidden())
                .and(ArticleSpecification.excludeHiddenCategories(hiddenCategories))
                .and(ArticleSpecification.excludeBlockedKeywords(blockedKeywords));
    }

    private List<Article> sortArticles(List<Article> articles, Sort sort) {
        if (sort == null || sort.isUnsorted()) {
            return articles;
        }

        Comparator<Article> comparator = null;

        for (Sort.Order order : sort) {
            Comparator<Article> currentComparator;

            switch (order.getProperty()) {
                case "likeCount" -> currentComparator = Comparator.comparingInt(Article::getLikeCount);
                case "dislikeCount" -> currentComparator = Comparator.comparingInt(Article::getDislikeCount);
                default -> {
                    continue;
                }
            }

            if (order.getDirection().isDescending()) {
                currentComparator = currentComparator.reversed();
            }

            comparator = (comparator == null) ? currentComparator : comparator.thenComparing(currentComparator);
        }

        if (comparator != null) {
            articles.sort(comparator);
        }

        return articles;

    }

}
