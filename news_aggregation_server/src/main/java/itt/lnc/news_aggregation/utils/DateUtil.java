package itt.lnc.news_aggregation.utils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate parseDate(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) return null;
        return OffsetDateTime.parse(timestamp).toLocalDate();
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
