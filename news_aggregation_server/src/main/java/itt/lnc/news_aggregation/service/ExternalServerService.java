package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ExternalServerDTO;

import java.util.List;

public interface ExternalServerService {
    List<ExternalServerDTO> getAllServers();
    ExternalServerDTO updateApiKey(Long id, String apiKey);
}
