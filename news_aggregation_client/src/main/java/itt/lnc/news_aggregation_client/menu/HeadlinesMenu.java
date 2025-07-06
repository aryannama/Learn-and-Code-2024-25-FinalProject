package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.ArticleHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.HEADLINES)
@Component
@RequiredArgsConstructor
public class HeadlinesMenu implements Menu {
    private final ArticleHandler articleHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "Today",
                    "Date range",
                    "Back"
            ));

            switch (choice) {
                case 1:
                    articleHandler.showTodayArticles();
                    return;
                case 2:
                    articleHandler.handleDateRangeSelection();
                    return;
                case 3:
                    menuContext.navigateTo(MenuType.USER);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
}
