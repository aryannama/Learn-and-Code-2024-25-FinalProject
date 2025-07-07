package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.ExternalServerHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.EXTERNAL_SERVER)
@Component
@RequiredArgsConstructor
public class ExternalServerMenu implements Menu {
    private final ExternalServerHandler externalServerHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "View the list of external servers and status",
                    "View the external server's details",
                    "Update/Edit the external server’s details",
                    "Back"
            ));
            switch (choice) {
                case 1:
                    externalServerHandler.viewExternalServers();
                    return;
                case 2:
                    externalServerHandler.viewExternalServerDetails();
                    return;
                case 3:
                    externalServerHandler.updateExternalServerDetails();
                    return;
                case 4:
                    menuContext.navigateTo(MenuType.ADMIN);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }

    }
}
