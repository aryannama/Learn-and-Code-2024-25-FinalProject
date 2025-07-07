package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ExternalServerDto;

import java.util.List;

public interface ExternalServerService {
    List<ExternalServerDto> getAllServers();
    void updateApiKey(Long id, String apiKey);
}
