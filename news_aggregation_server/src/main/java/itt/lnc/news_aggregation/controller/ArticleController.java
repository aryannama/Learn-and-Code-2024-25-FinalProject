package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ArticleFilter;
import itt.lnc.news_aggregation.dto.PaginatedResponse;
import itt.lnc.news_aggregation.security.SecurityContext;
import itt.lnc.news_aggregation.service.ArticleService;
import itt.lnc.news_aggregation.service.SavedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final SavedArticleService savedArticleService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ArticleDTO>> getAllArticles(@ModelAttribute ArticleFilter articleFilter, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(articleService.getAllArticles(articleFilter, pageable));
    }

    @GetMapping("/saved")
    public ResponseEntity<List<ArticleDTO>> getSavedArticles() {
        Long userId = SecurityContext.getCurrentUserId();
        List<ArticleDTO> saved = savedArticleService.getSavedArticles(userId);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<Void> toggleSave(@PathVariable Long id) {
        Long userId = SecurityContext.getCurrentUserId();
        savedArticleService.toggleSave(userId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticle(id);
        return ResponseEntity.ok(article);
    }


}
