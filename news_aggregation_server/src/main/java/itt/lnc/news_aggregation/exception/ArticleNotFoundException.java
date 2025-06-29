package itt.lnc.news_aggregation.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Article not found: " + id);
    }
}
