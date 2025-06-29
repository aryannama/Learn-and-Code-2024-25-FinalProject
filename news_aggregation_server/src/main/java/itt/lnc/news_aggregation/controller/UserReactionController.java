package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.security.SecurityContext;
import itt.lnc.news_aggregation.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reactions")
@RequiredArgsConstructor
public class UserReactionController {
    private final ReactionService userReactionService;

    @PostMapping("/{articleId}/like")
    public ResponseEntity<Void> toggleLike(@PathVariable Long articleId) {
        Long userId = SecurityContext.getCurrentUserId();
        userReactionService.toggleLike(userId, articleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{articleId}/dislike")
    public ResponseEntity<Void> toggleDislike(@PathVariable Long articleId) {
        Long userId = SecurityContext.getCurrentUserId();
        userReactionService.toggleDislike(userId, articleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{articleId}/report")
    public ResponseEntity<Void> reportArticle(@PathVariable Long articleId) {
        Long userId = SecurityContext.getCurrentUserId();
        userReactionService.toggleReport(userId, articleId);
        return ResponseEntity.ok().build();
    }
}
