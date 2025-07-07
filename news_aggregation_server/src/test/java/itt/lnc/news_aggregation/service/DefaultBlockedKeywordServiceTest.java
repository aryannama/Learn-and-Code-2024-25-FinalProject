package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.repository.BlockedKeywordRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultBlockedKeywordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultBlockedKeywordServiceTest {

    @Mock
    private BlockedKeywordRepository blockedKeywordRepository;

    private DefaultBlockedKeywordService blockedKeywordService;

    @BeforeEach
    void setUp() {
        blockedKeywordService = new DefaultBlockedKeywordService(blockedKeywordRepository);
    }

    @Test
    void testAddBlockedKeyword_shouldSaveKeyword() {
        String keyword = "politics";

        blockedKeywordService.addBlockedKeyword(keyword);

        ArgumentCaptor<BlockedKeyword> captor = ArgumentCaptor.forClass(BlockedKeyword.class);
        verify(blockedKeywordRepository, times(1)).save(captor.capture());

        assertEquals(keyword, captor.getValue().getKeyword());
    }

    @Test
    void testRemoveBlockedKeyword_shouldDeleteById() {
        Long id = 1L;

        blockedKeywordService.removeBlockedKeyword(id);

        verify(blockedKeywordRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllBlockedKeywords_shouldReturnList() {
        BlockedKeyword k1 = new BlockedKeyword();
        k1.setId(1L);
        k1.setKeyword("sports");

        when(blockedKeywordRepository.findAll()).thenReturn(List.of(k1));

        List<BlockedKeyword> result = blockedKeywordService.getAllBlockedKeywords();

        assertEquals(1, result.size());
        assertEquals("sports", result.get(0).getKeyword());
    }

    @Test
    void testGetAllBlockedKeywords_whenEmpty_shouldThrowException() {
        when(blockedKeywordRepository.findAll()).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> blockedKeywordService.getAllBlockedKeywords());

        assertEquals("No blocked keywords found", ex.getMessage());
    }
}
