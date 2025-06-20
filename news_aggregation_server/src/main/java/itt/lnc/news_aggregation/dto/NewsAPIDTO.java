package itt.lnc.news_aggregation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsAPIDTO {
    private String title;
    private String description;
    @JsonProperty("url")
    private String articleUrl;

    @JsonProperty("urlToImage")
    private String imageUrl;
    private String publishedAt;

}
