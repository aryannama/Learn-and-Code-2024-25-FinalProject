package itt.lnc.news_aggregation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TheNewsApiDto {
    private String title;
    private String description;
    private Set<String> categories;
    @JsonProperty("url")
    private String articleUrl;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("published_at")
    private String publishedAt;
}
