package itt.lnc.news_aggregation_client.dto;

import lombok.Data;

@Data
public class ExternalServerDto {
    private Long id;
    private String name;
    private String apiKey;
    private String baseUrl;
    private String status;
    private String lastAccessed;
}
