package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ReactionsResponse;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.model.UserReaction;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.UserReactionRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.implementations.UserReactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserReactionServiceTest {

    @Mock
    private UserReactionRepository reactionRepository;
    @Mock
    private ArticleRepository articleRepo;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleService articleService;

    @InjectMocks
    private UserReactionService service;

    private final Long userId = 1L;
    private final Long articleId = 10L;
    private Article article;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "articleReportThreshold", 3); // Inject threshold manually

        article = new Article();
        article.setId(articleId);
        article.setLikeCount(0);
        article.setDislikeCount(0);
        article.setReportCount(0);
        article.setHidden(false);
    }

    private UserReaction reactionWithDefaults() {
        UserReaction reaction = new UserReaction();
        reaction.setUser(new User());
        reaction.setArticle(article);
        return reaction;
    }

    @Test
    void toggleLike_newLike_shouldAddLike() {
        when(articleService.getArticleById(articleId)).thenReturn(article);
        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.empty());
        when(userRepository.getReferenceById(userId)).thenReturn(new User());

        service.toggleLike(userId, articleId);

        assertEquals(1, article.getLikeCount());
        verify(reactionRepository).save(any());
        verify(articleRepo).save(article);
    }

    @Test
    void toggleLike_removeExistingLike_shouldDecreaseLikeCount() {
        UserReaction reaction = reactionWithDefaults();
        reaction.setLiked(true);
        when(articleService.getArticleById(articleId)).thenReturn(article);
        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.of(reaction));

        service.toggleLike(userId, articleId);

        assertFalse(reaction.isLiked());
        assertEquals(-1, article.getLikeCount());
        verify(reactionRepository).save(reaction);
    }

    @Test
    void toggleDislike_addDislike_shouldWorkAndRemoveLike() {
        UserReaction reaction = reactionWithDefaults();
        reaction.setLiked(true);
        reaction.setDisliked(false);
        article.setLikeCount(1);

        when(articleService.getArticleById(articleId)).thenReturn(article);
        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.of(reaction));

        service.toggleDislike(userId, articleId);

        assertTrue(reaction.isDisliked());
        assertFalse(reaction.isLiked());
        assertEquals(0, article.getLikeCount());
        assertEquals(1, article.getDislikeCount());
        verify(reactionRepository).save(reaction);
    }

    @Test
    void toggleReport_newReport_shouldIncrementCountAndPossiblyHide() {
        UserReaction reaction = reactionWithDefaults();
        reaction.setReported(false);
        article.setReportCount(2);

        when(articleService.getArticleById(articleId)).thenReturn(article);
        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.of(reaction));

        service.toggleReport(userId, articleId);

        assertTrue(reaction.isReported());
        assertEquals(3, article.getReportCount());
        assertTrue(article.isHidden());
        verify(reactionRepository).save(reaction);
        verify(articleRepo).save(article);
    }

    @Test
    void getUserReaction_existingReaction_shouldReturnCorrectResponse() {
        UserReaction reaction = new UserReaction();
        reaction.setLiked(true);
        reaction.setDisliked(false);
        reaction.setReported(true);

        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.of(reaction));

        ReactionsResponse response = service.getUserReaction(userId, articleId);

        assertTrue(response.isLiked());
        assertFalse(response.isDisliked());
        assertTrue(response.isReported());
    }

    @Test
    void getUserReaction_notFound_shouldThrowException() {
        when(reactionRepository.findByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getUserReaction(userId, articleId));
    }
}
