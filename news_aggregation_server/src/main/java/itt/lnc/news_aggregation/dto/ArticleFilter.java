package itt.lnc.news_aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFilter {
    private String category;
    private String query;
    private LocalDate startDate;
    private LocalDate endDate;
}
