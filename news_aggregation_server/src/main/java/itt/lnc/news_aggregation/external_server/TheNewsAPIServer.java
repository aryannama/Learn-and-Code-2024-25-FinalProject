package itt.lnc.news_aggregation.external_server;

import com.fasterxml.jackson.databind.JsonNode;
import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.ExternalServerDto;
import itt.lnc.news_aggregation.dto.TheNewsApiDto;
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
    public List<ArticleDto> fetchArticles(ExternalServerDto externalServerDTO) throws IllegalArgumentException {
        String url = externalServerDTO.getBaseUrl().replace("<API_KEY>", externalServerDTO.getApiKey());
        HttpResponse<String> response = APIClient.get(url);
        JsonNode rootNode = JsonParser.toJsonNode(response.body());
        List<TheNewsApiDto> articles = JsonParser.parseList(rootNode.get("data"), TheNewsApiDto.class);
        return articles.stream()
                .map(theNewsApiDto -> modelMapper.map(theNewsApiDto, ArticleDto.class))
                .collect(Collectors.toList());
    }
}
