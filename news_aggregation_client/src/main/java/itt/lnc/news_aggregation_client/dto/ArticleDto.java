package itt.lnc.news_aggregation_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    private Long id;
    private String title;
    private String description;
    private Set<String> categories;
    private String articleUrl;
    private String imageUrl;
    private String publishedAt;
    private int reportCount;
    private int likeCount;
    private int dislikeCount;
}
