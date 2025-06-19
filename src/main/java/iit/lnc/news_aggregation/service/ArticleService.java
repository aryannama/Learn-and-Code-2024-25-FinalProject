package iit.lnc.news_aggregation.service;

import iit.lnc.news_aggregation.dto.ArticleDTO;
import iit.lnc.news_aggregation.mapper.ArticleMapper;
import iit.lnc.news_aggregation.model.Article;
import iit.lnc.news_aggregation.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public void saveArticles(List<ArticleDTO> articles) {
        List<Article> entities = articles.stream()
                .map(articleMapper::toEntity)
                .toList();
        articleRepository.saveAll(entities);
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found: " + id));
        return articleMapper.toDto(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
