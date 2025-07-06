package itt.lnc.news_aggregation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
        log.error(message);
    }
}
