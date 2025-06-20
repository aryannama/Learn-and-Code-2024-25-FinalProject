package itt.lnc.news_aggregation.dto;

import itt.lnc.news_aggregation.enums.ServerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalServerDTO {
    private Long id;
    private String name;
    private String apiKey;
    private String baseUrl;
    private ServerStatus status;
    private LocalDateTime lastAccessed;
}
