package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.NotificationConfigurationApiClient;
import itt.lnc.news_aggregation_client.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation_client.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationConfigurationService {
    private final NotificationConfigurationApiClient configurationClient;

    public List<NotificationConfigurationDto> getAllConfigurations() {
        return configurationClient.getAllNotificationConfigurations();
    }

    public void toggleCategory(Long categoryId, boolean enabled) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        configurationClient.toggleCategory(categoryId, enabled);
    }

    public void addKeyword(Long categoryId, String keyword) {
        validateCategoryIdAndKeyword(categoryId, keyword);
        configurationClient.addKeyword(categoryId, keyword);
    }

    public void removeKeyword(Long categoryId, String keyword) {
        validateCategoryIdAndKeyword(categoryId, keyword);
        configurationClient.removeKeyword(categoryId, keyword);
    }

    private void validateCategoryIdAndKeyword(Long categoryId, String keyword) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        if (!ValidationUtil.isNotBlank(keyword)) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }
    }
}
