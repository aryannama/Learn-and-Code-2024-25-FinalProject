package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.NotificationHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.NOTIFICATIONS)
@Component
@RequiredArgsConstructor
public class NotificationsMenu implements Menu {
    private final NotificationHandler notificationHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "View Notifications",
                    "Configure Notifications",
                    "Back",
                    "Logout"
            ));

            switch (choice) {
                case 1:
                    notificationHandler.viewAllNotifications();
                    return;
                case 2:
                    menuContext.navigateTo(MenuType.NOTIFICATION_CONFIGURATION);
                    return;
                case 3:
                    menuContext.navigateTo(MenuType.USER);
                    return;
                case 4:
                    menuContext.logout();
                    return;
                default:
                    ConsoleUtil.println("Invalid choice");
            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
}
