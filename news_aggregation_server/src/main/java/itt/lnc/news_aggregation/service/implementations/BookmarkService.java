package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.mapper.ArticleMapper;
import itt.lnc.news_aggregation.model.SavedArticle;
import itt.lnc.news_aggregation.repository.ArticleRepository;
import itt.lnc.news_aggregation.repository.SavedArticleRepository;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.SavedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService implements SavedArticleService {

    private final SavedArticleRepository savedArticleRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public void toggleSave(Long userId, Long articleId) {
        Optional<SavedArticle> existing = savedArticleRepository.findByUserIdAndArticleId(userId, articleId);

        if (existing.isPresent()) {
            savedArticleRepository.delete(existing.get());
        } else {
            SavedArticle saved = new SavedArticle();
            saved.setUser(userRepository.getReferenceById(userId));
            saved.setArticle(articleRepository.getReferenceById(articleId));
            savedArticleRepository.save(saved);
        }
    }

    public List<ArticleDTO> getSavedArticles(Long userId) {
        List<SavedArticle> savedArticles = savedArticleRepository.findAllByUserId(userId);
        return savedArticles.stream()
                .map(article -> articleMapper.toDto(article.getArticle()))
                .toList();
    }
}

