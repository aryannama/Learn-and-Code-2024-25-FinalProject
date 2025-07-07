package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.BlockedKeywordHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@MenuHandler(menuType = MenuType.MANAGE_KEYWORDS)
@Component
@RequiredArgsConstructor
public class ManageKeywordMenu implements Menu {
    private final BlockedKeywordHandler blockedKeywordHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "View all blocked keywords",
                    "Block keyword",
                    "Unblock keyword",
                    "Back"
            ));

            switch (choice) {
                case 1:
                    blockedKeywordHandler.listBlockedKeywords();
                    return;
                case 2:
                    blockedKeywordHandler.blockKeyword();
                    return;
                case 3:
                    blockedKeywordHandler.unblockKeyword();
                    return;
                case 4:
                    menuContext.navigateTo(MenuType.ADMIN);
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice");
            }
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }
}
