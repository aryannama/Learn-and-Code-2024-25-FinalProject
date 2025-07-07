package itt.lnc.news_aggregation_client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse<T> {
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<T> content;
}
