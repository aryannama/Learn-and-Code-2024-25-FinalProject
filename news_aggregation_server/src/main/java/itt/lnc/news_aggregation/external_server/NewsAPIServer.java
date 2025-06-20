package itt.lnc.news_aggregation.external_server;

import com.fasterxml.jackson.databind.JsonNode;
import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ExternalServerDTO;
import itt.lnc.news_aggregation.dto.NewsAPIDTO;
import itt.lnc.news_aggregation.utils.APIClient;
import itt.lnc.news_aggregation.utils.JsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static itt.lnc.news_aggregation.constants.ExternalServer.NEWS_API;

@Component(NEWS_API)
public class NewsAPIServer implements ExternalServer {
    private final ModelMapper modelMapper;

    public NewsAPIServer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleDTO> fetchArticles(ExternalServerDTO externalServerDTO) {
        String url = externalServerDTO.getBaseUrl().replace("<API_KEY>", externalServerDTO.getApiKey());
        HttpResponse<String> response = APIClient.get(url);
        JsonNode rootNode = JsonParser.toJsonNode(response.body());
        List<NewsAPIDTO> articles = JsonParser.parseList(rootNode.get("articles"), NewsAPIDTO.class);
        return articles.stream()
                .map(newsAPIDTO -> modelMapper.map(newsAPIDTO, ArticleDTO.class))
                .collect(Collectors.toList());
    }
}
