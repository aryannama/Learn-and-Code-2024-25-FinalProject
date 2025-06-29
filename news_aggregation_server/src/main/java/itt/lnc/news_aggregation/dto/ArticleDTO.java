package itt.lnc.news_aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String description;
    private Set<String> categories;
    private String articleUrl;
    private String imageUrl;
    private String publishedAt;
    private boolean hidden;
    private int reportCount;
    private int likeCount;
    private int dislikeCount;
}
