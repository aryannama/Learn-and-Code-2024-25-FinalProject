package itt.lnc.news_aggregation_client.exception;

public class UserCancelledSelectionException extends RuntimeException {
    public UserCancelledSelectionException() {
        super("Selection cancelled.");
    }
}
