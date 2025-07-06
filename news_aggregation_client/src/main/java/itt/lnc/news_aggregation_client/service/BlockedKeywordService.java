package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.BlockedKeywordApiClient;
import itt.lnc.news_aggregation_client.dto.BlockedKeywordDto;
import itt.lnc.news_aggregation_client.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlockedKeywordService {
    private final BlockedKeywordApiClient blockedKeywordApiClient;

    public List<BlockedKeywordDto> getAllBlockedKeywords() {
        return blockedKeywordApiClient.getBlockedKeywords();
    }

    public void addBlockedKeyword(String keyword) {

        if (!ValidationUtil.isNotBlank(keyword)) {
            throw new IllegalArgumentException("Blocked keyword cannot be empty");
        }

        blockedKeywordApiClient.addBlockedKeyword(keyword);
    }

    public void removeBlockedKeyword(Long id) {
        blockedKeywordApiClient.removeBlockedKeyword(id);
    }
}
