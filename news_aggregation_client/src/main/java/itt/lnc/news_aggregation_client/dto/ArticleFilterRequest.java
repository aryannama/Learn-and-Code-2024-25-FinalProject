package itt.lnc.news_aggregation_client.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class ArticleFilterRequest {
    private String category;
    private String query;
    private LocalDate startDate;
    private LocalDate endDate;

    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        if (category != null) params.put("category", category);
        if (query != null) params.put("query", query);
        if (startDate != null) params.put("startDate", startDate.toString());
        if (endDate != null) params.put("endDate", endDate.toString());
        return params;
    }
}
