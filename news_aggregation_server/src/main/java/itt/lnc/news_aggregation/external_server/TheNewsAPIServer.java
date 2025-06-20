package itt.lnc.news_aggregation.external_server;

import com.fasterxml.jackson.databind.JsonNode;
import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ExternalServerDTO;
import itt.lnc.news_aggregation.dto.TheNewsAPIDTO;
import itt.lnc.news_aggregation.utils.APIClient;
import itt.lnc.news_aggregation.utils.JsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static itt.lnc.news_aggregation.constants.ExternalServer.THE_NEWS_API;

@Component(THE_NEWS_API)
public class TheNewsAPIServer implements ExternalServer {
    private final ModelMapper modelMapper;

    public TheNewsAPIServer(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public List<ArticleDTO> fetchArticles(ExternalServerDTO externalServerDTO) {
        String url = externalServerDTO.getBaseUrl().replace("<API_KEY>", externalServerDTO.getApiKey());
        HttpResponse<String> response = APIClient.get(url);
        JsonNode rootNode = JsonParser.toJsonNode(response.body());
        List<TheNewsAPIDTO> articles = JsonParser.parseList(rootNode.get("data"), TheNewsAPIDTO.class);
        return articles.stream()
                .map(theNewsAPIDTO -> modelMapper.map(theNewsAPIDTO, ArticleDTO.class))
                .collect(Collectors.toList());
    }
}
