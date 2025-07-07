package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation_client.handler.NotificationHandler;
import itt.lnc.news_aggregation_client.service.NotificationConfigurationService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.NOTIFICATION_CONFIGURATION)
@Component
@RequiredArgsConstructor
public class NotificationConfigurationMenu implements Menu {
    private final NotificationHandler notificationHandler;
    private final NotificationConfigurationService configurationService;

    @Override
    public void display(MenuContext menuContext) {
        try {
            NotificationConfigurationDto selectedCategory = notificationHandler.selectConfigurationCategory();

            ConsoleUtil.printMessage(selectedCategory.getCategoryName() + " - " + (selectedCategory.isEnabled() ? "Enabled" : "Disabled"));
            int choice = ConsoleUtil.promptMenu(List.of("Toggle Category State", "Add Keywords", "Remove Keywords", "Back"));

            switch (choice) {
                case 1:
                    notificationHandler.toggleCategory(selectedCategory);
                    return;
                case 2:
                    notificationHandler.addKeyword(selectedCategory);
                    return;
                case 3:
                    notificationHandler.removeKeyword(selectedCategory);
                    return;
                case 4:
                    menuContext.navigateTo(MenuType.NOTIFICATIONS);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }
}
