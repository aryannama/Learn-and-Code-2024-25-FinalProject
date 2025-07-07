package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultReportedArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultReportedArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    private DefaultReportedArticleService reportedArticleService;

    @BeforeEach
    void setUp() {
        reportedArticleService = new DefaultReportedArticleService(articleRepository, articleMapper);
    }

    @Test
    void testGetAllReportedArticles_ReturnsList() {
        Article article1 = new Article();
        article1.setId(1L);
        article1.setTitle("Reported article");

        ArticleDto dto1 = new ArticleDto();
        dto1.setId(1L);
        dto1.setTitle("Reported article");

        when(articleRepository.findByReportCountGreaterThan(0)).thenReturn(List.of(article1));
        when(articleMapper.toDto(article1)).thenReturn(dto1);

        List<ArticleDto> result = reportedArticleService.getAllReportedArticles();

        assertEquals(1, result.size());
        assertEquals(dto1.getId(), result.get(0).getId());
        verify(articleRepository).findByReportCountGreaterThan(0);
        verify(articleMapper).toDto(article1);
    }

    @Test
    void testGetAllReportedArticles_NoArticles_ThrowsException() {
        when(articleRepository.findByReportCountGreaterThan(0)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> reportedArticleService.getAllReportedArticles());

        assertEquals("No reported articles found", ex.getMessage());
    }

    @Test
    void testHideReportedArticle_Success() {
        Article article = new Article();
        article.setId(1L);
        article.setHidden(false);

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        reportedArticleService.hideReportedArticle(1L);

        assertTrue(article.isHidden());
        verify(articleRepository).save(article);
    }

    @Test
    void testHideReportedArticle_NotFound_ThrowsException() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                reportedArticleService.hideReportedArticle(1L));
    }
}
