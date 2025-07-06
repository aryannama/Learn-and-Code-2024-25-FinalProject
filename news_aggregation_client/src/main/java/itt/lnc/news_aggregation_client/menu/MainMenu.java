package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.annotation.MenuHandler;
import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.handler.AuthenticationHandler;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

@MenuHandler(menuType = MenuType.MAIN)
@RequiredArgsConstructor
public class MainMenu implements Menu {

    private final AuthenticationHandler authenticationHandler;

    @Override
    public void display(MenuContext menuContext) {
        try {
            int choice = ConsoleUtil.promptMenu(List.of(
                    "Login",
                    "Register",
                    "Exit")
            );
            switch (choice) {
                case 1:
                    authenticationHandler.handleLogin();
                    return;
                case 2:
                    authenticationHandler.handleRegister();
                    return;
                case 3:
                    ConsoleUtil.println("Exiting the application. Goodbye!");
                    System.exit(0);
                default:
                    ConsoleUtil.println("Invalid choice. Try again.");
            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }

    }
}
