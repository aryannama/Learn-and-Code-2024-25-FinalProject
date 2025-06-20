package itt.lnc.news_aggregation.external_server;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.ExternalServerDTO;
import itt.lnc.news_aggregation.factory.ExternalServerFactory;
import itt.lnc.news_aggregation.mapper.ExternalServerMapper;
import itt.lnc.news_aggregation.repository.ExternalServerRepository;
import itt.lnc.news_aggregation.service.ArticleService;
import itt.lnc.news_aggregation.service.ExternalServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static itt.lnc.news_aggregation.enums.ServerStatus.ACTIVE;
import static itt.lnc.news_aggregation.enums.ServerStatus.INACTIVE;

@Component
@RequiredArgsConstructor
public class NewsFetcher {
    private final ExternalServerService externalServerService;
    private final ExternalServerRepository externalServerRepository;
    private final ExternalServerFactory externalServerFactory;
    private final ExternalServerMapper externalServerMapper;
    private final ArticleService articleService;

    public void fetchNews() {
        List<ExternalServerDTO> externalServers = externalServerService.getAllServers();
        for (ExternalServerDTO server : externalServers) {
            try {
                ExternalServer externalServer = externalServerFactory.getExternalServer(server.getName());
                List<ArticleDTO> articles = externalServer.fetchArticles(server);

                if (!articles.isEmpty()) {
                    server.setLastAccessed(LocalDateTime.now());
                    server.setStatus(ACTIVE);
                    externalServerRepository.save(externalServerMapper.toEntity(server));
                    articleService.saveArticles(articles);
                    return;
                }
            } catch (Exception exception) {
                server.setStatus(INACTIVE);
                externalServerRepository.save(externalServerMapper.toEntity(server));
            }
        }
    }
}
