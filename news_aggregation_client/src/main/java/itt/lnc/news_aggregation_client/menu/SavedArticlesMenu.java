package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.ArticleHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.SAVED_ARTICLES)
@Component
@RequiredArgsConstructor
public class SavedArticlesMenu implements Menu {
    private final ArticleHandler articleHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "View Saved Articles",
                    "Back"
            ));

            switch (choice) {
                case 1:
                    articleHandler.showSavedArticles();
                    return;
                case 2:
                    menuContext.navigateTo(MenuType.USER);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }

    }
}
