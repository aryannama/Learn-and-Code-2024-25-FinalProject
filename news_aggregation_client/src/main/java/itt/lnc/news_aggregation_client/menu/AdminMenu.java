package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.service.CategoryService;
import itt.lnc.news_aggregation_client.service.ExternalServerService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.ADMIN)
@Component
@RequiredArgsConstructor
public class AdminMenu implements Menu {
    private final ExternalServerService externalServerService;
    private final CategoryService categoryService;

    @Override
    public void display(MenuContext menuContext) {
        ConsoleUtil.printHeader("WELCOME " + SessionManager.getName() + " : " + SessionManager.getRole());
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "Manage external servers",
                    "Manage Categories",
                    "Manage Blocked Keywords",
                    "Manage Reported Articles",
                    "Logout"
            ));
            switch (choice) {

                case 1:
                    menuContext.navigateTo(MenuType.EXTERNAL_SERVER);
                    return;
                case 2:
                    menuContext.navigateTo(MenuType.MANAGE_CATEGORIES);
                    return;
                case 3:
                    menuContext.navigateTo(MenuType.MANAGE_KEYWORDS);
                    return;
                case 4:
                    menuContext.navigateTo(MenuType.MANAGE_REPORTED_ARTICLES);
                    return;
                case 5:
                    menuContext.logout();
                    return;
                default:
                    ConsoleUtil.println("Invalid choice. Try again.");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }


}
