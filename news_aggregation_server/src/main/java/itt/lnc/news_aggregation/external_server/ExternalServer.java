package itt.lnc.news_aggregation.external_server;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.ExternalServerDto;

import java.util.List;

public interface ExternalServer {
    List<ArticleDto> fetchArticles(ExternalServerDto externalServerDTO);
}
