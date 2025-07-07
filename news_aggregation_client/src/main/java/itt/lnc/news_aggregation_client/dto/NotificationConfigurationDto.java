package itt.lnc.news_aggregation_client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationConfigurationDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private boolean enabled;
    private Set<String> keywords;
}