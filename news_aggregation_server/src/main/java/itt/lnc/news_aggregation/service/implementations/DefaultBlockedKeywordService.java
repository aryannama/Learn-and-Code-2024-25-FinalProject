package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.repository.BlockedKeywordRepository;
import itt.lnc.news_aggregation.service.BlockedKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultBlockedKeywordService implements BlockedKeywordService {
    private final BlockedKeywordRepository blockedKeywordRepository;

    @Override
    public BlockedKeyword addBlockedKeyword(String keyword) {
        BlockedKeyword blockedKeyword = new BlockedKeyword();
        blockedKeyword.setKeyword(keyword);
        return blockedKeywordRepository.save(blockedKeyword);
    }

    @Override
    public void removeBlockedKeyword(Long id) {
        blockedKeywordRepository.deleteById(id);
    }

    @Override
    public List<BlockedKeyword> getAllBlockedKeywords() {
        return blockedKeywordRepository.findAll();
    }
} 