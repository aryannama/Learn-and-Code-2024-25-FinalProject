package itt.lnc.news_aggregation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
        log.error("Bad Credentials Exception: {}", message);
    }
}
