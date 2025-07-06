package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.ExternalServerApiClient;
import itt.lnc.news_aggregation_client.dto.ExternalServerDto;
import itt.lnc.news_aggregation_client.dto.UpdateExternalServerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExternalServerService {

    private final ExternalServerApiClient externalServerApiClient;

    public List<ExternalServerDto> getAllServers() {
        return externalServerApiClient.getAllServers();
    }

    public void updateServerApiKey(Long id, String apiKey) {
        UpdateExternalServerDto request = new UpdateExternalServerDto(apiKey);
        externalServerApiClient.updateServerApiKey(id, request);
    }
}
