package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.SavedArticle;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.SavedArticleRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.implementations.BookmarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private SavedArticleRepository savedArticleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    private final Long userId = 1L;
    private final Long articleId = 10L;

    private User mockUser;
    private Article mockArticle;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(userId);

        mockArticle = new Article();
        mockArticle.setId(articleId);
    }

    @Test
    void toggleSave_shouldRemoveSavedArticle_ifAlreadyExists() {
        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setUser(mockUser);
        savedArticle.setArticle(mockArticle);

        when(savedArticleRepository.findByUserIdAndArticleId(userId, articleId))
                .thenReturn(Optional.of(savedArticle));

        bookmarkService.toggleSave(userId, articleId);

        verify(savedArticleRepository).delete(savedArticle);
        verify(savedArticleRepository, never()).save(any());
    }

    @Test
    void toggleSave_shouldAddSavedArticle_ifNotExists() {
        when(savedArticleRepository.findByUserIdAndArticleId(userId, articleId))
                .thenReturn(Optional.empty());

        when(userRepository.getReferenceById(userId)).thenReturn(mockUser);
        when(articleRepository.getReferenceById(articleId)).thenReturn(mockArticle);

        bookmarkService.toggleSave(userId, articleId);

        ArgumentCaptor<SavedArticle> captor = ArgumentCaptor.forClass(SavedArticle.class);
        verify(savedArticleRepository).save(captor.capture());

        SavedArticle saved = captor.getValue();
        assertThat(saved.getUser()).isEqualTo(mockUser);
        assertThat(saved.getArticle()).isEqualTo(mockArticle);
    }

    @Test
    void getSavedArticles_shouldReturnMappedDtoList_ifArticlesExist() {
        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setArticle(mockArticle);

        ArticleDto dto = new ArticleDto();
        dto.setId(articleId);

        when(savedArticleRepository.findAllByUserId(userId))
                .thenReturn(List.of(savedArticle));
        when(articleMapper.toDto(mockArticle)).thenReturn(dto);

        List<ArticleDto> result = bookmarkService.getSavedArticles(userId);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(articleId);
    }

    @Test
    void getSavedArticles_shouldThrowResourceNotFoundException_ifEmpty() {
        when(savedArticleRepository.findAllByUserId(userId)).thenReturn(List.of());

        assertThatThrownBy(() -> bookmarkService.getSavedArticles(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No saved articles found.");
    }
}
