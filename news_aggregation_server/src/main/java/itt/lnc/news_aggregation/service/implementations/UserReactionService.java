package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.UserReaction;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.UserReactionRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.ArticleService;
import itt.lnc.news_aggregation.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReactionService implements ReactionService {

    @Value("${article.report.threshold}")
    private int articleReportThreshold;

    private final UserReactionRepository reactionRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleService articleService;

    public void toggleLike(Long userId, Long articleId) {
        Article article = articleService.getArticleById(articleId);
        UserReaction reaction = getOrCreateReaction(userId, article);

        if (reaction.isLiked()) {
            reaction.setLiked(false);
            article.setLikeCount(article.getLikeCount() - 1);
        } else {
            reaction.setLiked(true);
            article.setLikeCount(article.getLikeCount() + 1);

            if (reaction.isDisliked()) {
                reaction.setDisliked(false);
                article.setDislikeCount(article.getDislikeCount() - 1);
            }
        }

        saveReactionAndArticle(reaction, article);
    }

    public void toggleDislike(Long userId, Long articleId) {
        Article article = articleService.getArticleById(articleId);
        UserReaction reaction = getOrCreateReaction(userId, article);

        if (reaction.isDisliked()) {
            reaction.setDisliked(false);
            article.setDislikeCount(article.getDislikeCount() - 1);
        } else {
            reaction.setDisliked(true);
            article.setDislikeCount(article.getDislikeCount() + 1);

            if (reaction.isLiked()) {
                reaction.setLiked(false);
                article.setLikeCount(article.getLikeCount() - 1);
            }
        }

        saveReactionAndArticle(reaction, article);
    }

    public void toggleReport(Long userId, Long articleId) {
        Article article = articleService.getArticleById(articleId);
        UserReaction reaction = getOrCreateReaction(userId, article);

        if (!reaction.isReported()) {
            reaction.setReported(true);
            article.setReportCount(article.getReportCount() + 1);

            if (article.getReportCount() >= articleReportThreshold) {
                article.setHidden(true);
            }

            saveReactionAndArticle(reaction, article);
        }
    }

    private UserReaction getOrCreateReaction(Long userId, Article article) {
        return reactionRepository.findByUserIdAndArticleId(userId, article.getId())
                .orElseGet(() -> {
                    UserReaction reaction = new UserReaction();
                    reaction.setUser(userRepository.getReferenceById(userId));
                    reaction.setArticle(article);
                    return reaction;
                });
    }

    private void saveReactionAndArticle(UserReaction reaction, Article article) {
        reactionRepository.save(reaction);
        articleRepository.save(article);
    }
}

