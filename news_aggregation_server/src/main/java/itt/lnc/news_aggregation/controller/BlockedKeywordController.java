package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.service.BlockedKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocked-keywords")
@RequiredArgsConstructor
public class BlockedKeywordController {
    private final BlockedKeywordService blockedKeywordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBlockedKeyword(@RequestParam String keyword) {
        blockedKeywordService.addBlockedKeyword(keyword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBlockedKeyword(@PathVariable Long id) {
        blockedKeywordService.removeBlockedKeyword(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BlockedKeyword>> getAllBlockedKeywords() {
        List<BlockedKeyword> keywords = blockedKeywordService.getAllBlockedKeywords();
        return ResponseEntity.ok(keywords);
    }
} 