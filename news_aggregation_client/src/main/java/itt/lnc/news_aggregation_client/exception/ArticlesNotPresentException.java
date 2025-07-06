package itt.lnc.news_aggregation_client.exception;

public class ArticlesNotPresentException extends RuntimeException {
    public ArticlesNotPresentException(String message) {
        super(message);
    }
}
