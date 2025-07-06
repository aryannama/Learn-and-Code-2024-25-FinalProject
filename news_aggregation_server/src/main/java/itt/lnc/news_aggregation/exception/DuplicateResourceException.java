package itt.lnc.news_aggregation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
        log.error("Duplicate resource error: {}", message);
    }
}
