package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.ReportedArticleHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.MANAGE_REPORTED_ARTICLES)
@Component
@RequiredArgsConstructor
public class ManageReportedArticlesMenu implements Menu {
    private final ReportedArticleHandler reportedArticleHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(
                    List.of(
                            "View Reported Articles",
                            "Hide Reported Article",
                            "Back"
                    ));

            switch (choice) {
                case 1:
                    reportedArticleHandler.listReportedArticles();
                    return;
                case 2:
                    reportedArticleHandler.hideReportedArticle();
                    return;
                case 3:
                    menuContext.navigateTo(MenuType.ADMIN);

            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
}
