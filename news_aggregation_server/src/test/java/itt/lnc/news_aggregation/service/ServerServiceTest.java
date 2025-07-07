package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ExternalServerDto;
import itt.lnc.news_aggregation.model.ExternalServer;
import itt.lnc.news_aggregation.repository.ExternalServerRepository;
import itt.lnc.news_aggregation.service.implementations.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerServiceTest {

    @Mock
    private ExternalServerRepository externalServerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServerService serverService;

    private ExternalServer server1;
    private ExternalServerDto dto1;

    @BeforeEach
    void setUp() {
        server1 = new ExternalServer();
        server1.setId(1L);
        server1.setName("NewsAPI");
        server1.setApiKey("abc");

        dto1 = new ExternalServerDto();
        dto1.setId(1L);
        dto1.setName("NewsAPI");
    }

    @Test
    void testGetAllServers_returnsMappedDtos() {
        when(externalServerRepository.findAll()).thenReturn(List.of(server1));
        when(modelMapper.map(server1, ExternalServerDto.class)).thenReturn(dto1);

        List<ExternalServerDto> result = serverService.getAllServers();

        assertEquals(1, result.size());
        assertEquals("NewsAPI", result.get(0).getName());
        verify(externalServerRepository).findAll();
        verify(modelMapper).map(server1, ExternalServerDto.class);
    }

    @Test
    void testUpdateApiKey_success() {
        when(externalServerRepository.findById(1L)).thenReturn(Optional.of(server1));

        serverService.updateApiKey(1L, "newKey");

        assertEquals("newKey", server1.getApiKey());
        verify(externalServerRepository).save(server1);
    }

    @Test
    void testUpdateApiKey_serverNotFound_throwsException() {
        when(externalServerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> serverService.updateApiKey(1L, "newKey"));

        assertEquals("Server not found", exception.getMessage());
    }
}
