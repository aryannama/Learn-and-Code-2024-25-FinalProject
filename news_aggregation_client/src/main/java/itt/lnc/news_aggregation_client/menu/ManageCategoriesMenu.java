package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.CategoryHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.MANAGE_CATEGORIES)
@Component
@RequiredArgsConstructor
public class ManageCategoriesMenu implements Menu {
    private final CategoryHandler categoryHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "View All Categories",
                    "Add Category",
                    "Hide Category",
                    "Unhide Category",
                    "Back"
            ));
            switch (choice) {
                case 1:
                    categoryHandler.listCategories();
                    return;
                case 2:
                    categoryHandler.addCategory();
                    return;
                case 3:
                    categoryHandler.hideCategory();
                    return;
                case 4:
                    categoryHandler.unhideCategory();
                    return;
                case 5:
                    menuContext.navigateTo(MenuType.ADMIN);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
}
