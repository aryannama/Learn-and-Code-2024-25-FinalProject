package itt.lnc.news_aggregation.utils;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationUtil {
    public static <T> List<T> paginate(List<T> items, Pageable pageable) {
        return items.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }

    public static int calculateTotalPages(int totalItems, int pageSize) {
        return (int) Math.ceil((double) totalItems / pageSize);
    }
}
