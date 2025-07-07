package itt.lnc.news_aggregation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExternalServerException extends RuntimeException {
    public ExternalServerException(String message) {
        super(message);
        log.error("External server error: {}", message);
    }
}
