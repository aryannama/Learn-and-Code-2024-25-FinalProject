package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.model.BlockedKeyword;

import java.util.List;

public interface BlockedKeywordService {
    void addBlockedKeyword(String keyword);
    void removeBlockedKeyword(Long id);
    List<BlockedKeyword> getAllBlockedKeywords();
} 