package itt.lnc.news_aggregation_client.factory;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class MenuFactory {
    private final Map<MenuType, Menu> menus = new EnumMap<>(MenuType.class);

    public MenuFactory(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MenuHandler.class);

        for (Object bean : beans.values()) {
            if (bean instanceof Menu menu) {
                MenuHandler menuHandler = bean.getClass().getAnnotation(MenuHandler.class);
                menus.put(menuHandler.menuType(), menu);
            }
        }
    }

    public Menu getMenu(MenuType menuType) {
        Menu menu = menus.get(menuType);
        if (menu == null) {
            throw new IllegalArgumentException("Menu not found for type: " + menuType);
        }
        return menu;
    }
}
