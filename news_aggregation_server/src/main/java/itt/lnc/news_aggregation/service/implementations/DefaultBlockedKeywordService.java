package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.repository.BlockedKeywordRepository;
import itt.lnc.news_aggregation.service.BlockedKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultBlockedKeywordService implements BlockedKeywordService {
    private final BlockedKeywordRepository blockedKeywordRepository;

    @Override
    public void addBlockedKeyword(String keyword) {
        log.info("Adding blocked keyword: {}", keyword);
        BlockedKeyword blockedKeyword = new BlockedKeyword();
        blockedKeyword.setKeyword(keyword);
        blockedKeywordRepository.save(blockedKeyword);
        log.info("Added blocked keyword: {}", blockedKeyword.getKeyword());
    }

    @Override
    public void removeBlockedKeyword(Long id) {
        log.info("Removing blocked keyword: {}", id);
        blockedKeywordRepository.deleteById(id);
        log.info("Removed blocked keyword with ID: {}", id);
    }

    @Override
    public List<BlockedKeyword> getAllBlockedKeywords() {
        log.info("Retrieving all blocked keywords");
        List<BlockedKeyword> blockedKeywords = blockedKeywordRepository.findAll();
        if (blockedKeywords.isEmpty()) {
            log.warn("No blocked keywords found");
            throw new ResourceNotFoundException("No blocked keywords found");
        }
        return blockedKeywords;
    }
} 