package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.dto.ExternalServerDto;
import itt.lnc.news_aggregation_client.service.ExternalServerService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExternalServerHandler {

    private final ExternalServerService externalServerService;

    public void updateExternalServerDetails() {
        ConsoleUtil.printMessage("Updating external server details");
        Long serverId = (long) ConsoleUtil.readInt("Enter the external server ID to update: ");
        String apiKey = ConsoleUtil.readLine("Enter the new API key: ");
        externalServerService.updateServerApiKey(serverId, apiKey);
    }

    public void viewExternalServerDetails() {
        ConsoleUtil.println("External servers details:");
        List<ExternalServerDto> servers = externalServerService.getAllServers();
        for (int index = 0; index < servers.size(); index++) {
            ExternalServerDto server = servers.get(index);
            ConsoleUtil.println((index + 1) + ". " + server.getName() + " - " + server.getApiKey());
        }
        ConsoleUtil.printSeparatorLine();
    }

    public void viewExternalServers() {
        ConsoleUtil.println("External servers list:");
        List<ExternalServerDto> servers = externalServerService.getAllServers();
        for (int index = 0; index < servers.size(); index++) {
            ExternalServerDto server = servers.get(index);
            ConsoleUtil.println((index + 1) + ". " + server.getName() + " - " + server.getStatus() + " - last accessed: " + server.getLastAccessed());
        }
        ConsoleUtil.printSeparatorLine();
    }

}
