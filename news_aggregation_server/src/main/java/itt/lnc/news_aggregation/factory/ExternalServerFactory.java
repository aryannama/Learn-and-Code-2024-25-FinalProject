package itt.lnc.news_aggregation.factory;

import itt.lnc.news_aggregation.external_server.ExternalServer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExternalServerFactory {
    private final Map<String, ExternalServer> servers;

    public ExternalServerFactory(Map<String, ExternalServer> servers) {
        this.servers = servers;
    }

    public ExternalServer getExternalServer(String externalServer) {
        ExternalServer server = servers.get(externalServer);
        if (server == null) {
            throw new IllegalArgumentException("No server found for: " + externalServer);
        }
        return server;
    }
}
