package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultArticleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private CategoryService categoryService;
    @Mock
    private BlockedKeywordService blockedKeywordService;
    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private DefaultArticleService articleService;

    @Test
    void saveArticles_shouldMapAndSaveAll() {
        List<ArticleDto> articleDto = List.of(new ArticleDto(/*...*/));
        List<Article> entities = List.of(new Article(/*...*/));

        when(articleMapper.toEntity(any())).thenReturn(entities.getFirst());
        when(articleRepository.saveAll(any())).thenReturn(entities);

        List<Article> result = articleService.saveArticles(articleDto);

        assertEquals(1, result.size());
        verify(articleRepository).saveAll(any());
    }

    @Test
    void getArticleById_shouldReturnArticle_whenExists() {
        Article article = new Article();
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(1L);
        assertEquals(article, result);
    }

    @Test
    void getArticleById_shouldThrow_whenNotFound() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> articleService.getArticleById(1L));
    }

    @Test
    void getAllArticles_shouldReturnPaginatedResponse() {
        Pageable pageable = PageRequest.of(0, 10);
        ArticleFilter filter = new ArticleFilter();

        Article article = Article.builder()
                .id(1L)
                .title("Test")
                .description("Description...")
                .likeCount(5)
                .dislikeCount(1)
                .hidden(false)
                .build();

        List<Article> allArticles = List.of(article);
        List<Category> hiddenCategories = List.of();
        List<BlockedKeyword> blockedKeywords = List.of();

        when(categoryService.getHiddenCategories()).thenReturn(hiddenCategories);
        when(blockedKeywordService.getAllBlockedKeywords()).thenReturn(blockedKeywords);
        when(articleRepository.findAll(any(Specification.class))).thenReturn(allArticles);
        lenient().when(recommendationService.getRecommendedArticlesForUser(any(), any()))
                .thenReturn(List.of());

        when(articleMapper.toDto(any())).thenReturn(new ArticleDto());

        PaginatedResponse<ArticleDto> response = articleService.getAllArticles(filter, pageable);

        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getCurrentPage());
        assertEquals(10, response.getPageSize());
        verify(articleRepository).findAll(any(Specification.class));
    }

    @Test
    void deleteArticle_shouldCallRepository() {
        articleService.deleteArticle(42L);
        verify(articleRepository).deleteById(42L);
    }
}
