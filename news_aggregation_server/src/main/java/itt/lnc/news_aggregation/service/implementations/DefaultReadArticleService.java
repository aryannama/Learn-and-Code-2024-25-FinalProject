package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.ReadArticle;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.ReadArticleRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.ReadArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReadArticleService implements ReadArticleService {
    private final ReadArticleRepository readArticleRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void markAsRead(Long userId, Long articleId) {
        if (readArticleRepository.existsByUserIdAndArticleId(userId, articleId)) return;
        User user = userRepository.getReferenceById(userId);
        Article article = articleRepository.getReferenceById(articleId);
        ReadArticle readArticle = ReadArticle.builder()
            .user(user)
            .article(article)
            .readAt(LocalDateTime.now())
            .build();
        readArticleRepository.save(readArticle);
    }

} 