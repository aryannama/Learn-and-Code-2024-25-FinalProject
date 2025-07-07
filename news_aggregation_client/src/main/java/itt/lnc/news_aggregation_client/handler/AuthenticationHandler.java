package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.constants.Role;
import itt.lnc.news_aggregation_client.dto.LoginRequest;
import itt.lnc.news_aggregation_client.dto.RegisterRequest;
import itt.lnc.news_aggregation_client.menu.MenuContext;
import itt.lnc.news_aggregation_client.service.AuthService;
import itt.lnc.news_aggregation_client.service.UserService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import itt.lnc.news_aggregation_client.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler {
    private final AuthService authService;
    private final UserService userService;
    private MenuContext menuContext;

    @Autowired
    public void setMenuContext(@Lazy MenuContext menuContext) {
        this.menuContext = menuContext;
    }

    public void handleLogin() {
        String email = ConsoleUtil.readLine("Email: ");
        if (!ValidationUtil.isValidEmail(email)) {
            ConsoleUtil.printError("Invalid email format.");
            return;
        }
        String password = ConsoleUtil.readLine("Password: ");
        if (!ValidationUtil.isNotBlank(password)) {
            ConsoleUtil.printError("Password cannot be empty.");
            return;
        }
        authService.login(new LoginRequest(email, password));
        userService.setCurrentUser();
        if (SessionManager.getRole().equalsIgnoreCase(Role.ADMIN.name())) {
            menuContext.navigateTo(MenuType.ADMIN);
        } else {
            menuContext.navigateTo(MenuType.USER);
        }
    }

    public void handleRegister() {
        String username = ConsoleUtil.readLine("Username: ");
        if (!ValidationUtil.isNotBlank(username)) {
            ConsoleUtil.printError("Username cannot be blank.");
            return;
        }
        String email = ConsoleUtil.readLine("Email: ");
        if (!ValidationUtil.isValidEmail(email)) {
            ConsoleUtil.printError("Invalid email format.");
            return;
        }
        String password = ConsoleUtil.readLine("Password: ");
        if (!ValidationUtil.isNotBlank(password)) {
            ConsoleUtil.printError("Password cannot be empty.");
            return;
        }
        authService.register(new RegisterRequest(username, email, password));
    }

}
