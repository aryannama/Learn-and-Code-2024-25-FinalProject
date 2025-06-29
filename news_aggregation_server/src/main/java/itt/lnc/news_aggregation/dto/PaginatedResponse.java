package itt.lnc.news_aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaginatedResponse<T> {
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<T> content;
}
