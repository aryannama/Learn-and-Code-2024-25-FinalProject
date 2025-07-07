package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.factory.MenuFactory;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.stereotype.Component;

@Component
public class MenuContext {
    private Menu currentMenu;
    private final MenuFactory menuFactory;

    public MenuContext(MenuFactory menuFactory) {
        this.menuFactory = menuFactory;
        this.currentMenu = menuFactory.getMenu(MenuType.MAIN);
    }

    public void start() {
        while (true) {
            currentMenu.display(this);
        }
    }

    public void navigateTo(MenuType menuType) {
        this.currentMenu = menuFactory.getMenu(menuType);
    }

    public void logout() {
        SessionManager.clear();
        this.currentMenu = menuFactory.getMenu(MenuType.MAIN);
    }
}
