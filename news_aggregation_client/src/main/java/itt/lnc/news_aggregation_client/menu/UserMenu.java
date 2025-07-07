package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.ArticleHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.USER)
@Component
@RequiredArgsConstructor
public class UserMenu implements Menu {
    private final ArticleHandler articleHandler;

    @Override
    public void display(MenuContext menuContext) {
        ConsoleUtil.printHeader("WELCOME " + SessionManager.getName());
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "Headlines",
                    "Saved Articles",
                    "Search Articles",
                    "Notifications",
                    "Logout"
            ));
            switch (choice) {
                case 1:
                    menuContext.navigateTo(MenuType.HEADLINES);
                    return;
                case 2:
                    menuContext.navigateTo(MenuType.SAVED_ARTICLES);
                    return;
                case 3:
                    articleHandler.searchArticles();
                    return;
                case 4:
                    menuContext.navigateTo(MenuType.NOTIFICATIONS);
                    return;
                case 5:
                    menuContext.logout();
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }
}
