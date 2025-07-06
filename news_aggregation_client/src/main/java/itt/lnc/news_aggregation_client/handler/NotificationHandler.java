package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.constants.AppConstants;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation_client.dto.NotificationDto;
import itt.lnc.news_aggregation_client.menu.MenuContext;
import itt.lnc.news_aggregation_client.service.NotificationConfigurationService;
import itt.lnc.news_aggregation_client.service.NotificationService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.ListSelector;
import itt.lnc.news_aggregation_client.utils.PaginatedListSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationHandler {
    private final NotificationService notificationService;
    private final NotificationConfigurationService configurationService;
    private MenuContext menuContext;

    @Autowired
    public void setMenuContext(@Lazy MenuContext menuContext) {
        this.menuContext = menuContext;
    }

    public void viewAllNotifications() {
        PaginatedListSelector<NotificationDto> selector = new PaginatedListSelector<>(
                page -> notificationService.getNotifications(page, AppConstants.PAGE_SIZE),
                notification -> String.format(" [ID: %s] %s\n %s",
                        notification.getArticleId(),
                        notification.getArticleTitle(),
                        notification.getTimestamp()));

        selector.select("Choose action to perform:", false);
    }

    public void configureNotifications() {
        List<NotificationConfigurationDto> configurations = configurationService.getAllConfigurations();
        ConsoleUtil.printMessage("Configure Notifications");
        ListSelector<NotificationConfigurationDto> selector = new ListSelector<>(configurations, configuration -> String.format("%s - %s",
                configuration.getCategoryName(),
                configuration.isEnabled() ? "Enabled" : "Disabled"));

        NotificationConfigurationDto selectedCategory = selector.select("Select a configuration to toggle");
        handleConfigurationCategory(selectedCategory);
    }

    public void handleConfigurationCategory(NotificationConfigurationDto configuration) {
        ConsoleUtil.printMessage(configuration.getCategoryName() + " - " + (configuration.isEnabled() ? "Enabled" : "Disabled"));
        int choice = ConsoleUtil.promptMenu(List.of("Toggle Category State", "Add Keywords", "Remove Keywords", "Back"));

        switch (choice) {
            case 1:
                configurationService.toggleCategory(configuration.getId(), !configuration.isEnabled());
                ConsoleUtil.printMessage("Configuration updated successfully.");
                return;
            case 2:
                ConsoleUtil.println("Keywords: " + String.join(", ", configuration.getKeywords()));
                String keyword = ConsoleUtil.readLine("Add new keyword:");
                configurationService.addKeyword(configuration.getId(), keyword);
                ConsoleUtil.printMessage("Keywords added successfully.");
                return;
            case 3:
                ConsoleUtil.println("Keywords: " + String.join(", ", configuration.getKeywords()));
                String removeKeyword = ConsoleUtil.readLine("Enter keyword to remove:");
                configurationService.removeKeyword(configuration.getId(), removeKeyword);
                ConsoleUtil.printMessage("Keywords removed successfully.");
                return;
            case 4:
                menuContext.navigateTo(MenuType.NOTIFICATIONS);
                return;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
    }
}
