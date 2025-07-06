package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ExternalServerDto;
import itt.lnc.news_aggregation.model.ExternalServer;
import itt.lnc.news_aggregation.repository.ExternalServerRepository;
import itt.lnc.news_aggregation.service.ExternalServerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServerService implements ExternalServerService {

    private final ExternalServerRepository externalServerRepository;
    private final ModelMapper modelMapper;

    public List<ExternalServerDto> getAllServers() {
        return externalServerRepository.findAll().stream()
                .map(externalServer -> modelMapper.map(externalServer, ExternalServerDto.class))
                .collect(Collectors.toList());
    }

    public void updateApiKey(Long id, String apiKey) {
        ExternalServer externalServer = externalServerRepository.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
        externalServer.setApiKey(apiKey);
        externalServerRepository.save(externalServer);
    }
}
