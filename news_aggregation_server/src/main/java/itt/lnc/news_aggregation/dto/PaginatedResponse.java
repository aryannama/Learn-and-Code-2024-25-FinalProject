package itt.lnc.news_aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

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

    public PaginatedResponse(Page<?> page, List<T> content) {
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.content = content;
    }
}
