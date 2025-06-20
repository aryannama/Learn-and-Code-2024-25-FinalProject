package itt.lnc.news_aggregation.external_server;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ExternalServerDTO;

import java.util.List;

public interface ExternalServer {
    List<ArticleDTO> fetchArticles(ExternalServerDTO externalServerDTO);
}
