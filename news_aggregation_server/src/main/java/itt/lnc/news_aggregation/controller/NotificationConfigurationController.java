package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation.security.SecurityContext;
import itt.lnc.news_aggregation.service.NotificationConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-configurations")
@RequiredArgsConstructor
public class NotificationConfigurationController {
    private final NotificationConfigurationService notificationConfigurationService;

    @GetMapping
    public ResponseEntity<List<NotificationConfigurationDto>> getUserNotificationConfigurations() {
        Long userId = SecurityContext.getCurrentUserId();
        return ResponseEntity.ok(notificationConfigurationService.getUserNotificationConfiguration(userId));
    }

    @PostMapping("/{categoryId}/toggle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleCategory(@PathVariable Long categoryId, @RequestParam boolean enabled) {
        Long userId = SecurityContext.getCurrentUserId();
        notificationConfigurationService.toggleCategory(userId, categoryId, enabled);
    }

    @PostMapping("/{categoryId}/keywords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addKeyword(@PathVariable Long categoryId, @RequestParam String keyword) {
        Long userId = SecurityContext.getCurrentUserId();
        notificationConfigurationService.addKeyword(userId, categoryId, keyword);
    }

    @DeleteMapping("/{categoryId}/keywords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeKeyword(@PathVariable Long categoryId, @RequestParam String keyword) {
        Long userId = SecurityContext.getCurrentUserId();
        notificationConfigurationService.removeKeyword(userId, categoryId, keyword);
    }
}
