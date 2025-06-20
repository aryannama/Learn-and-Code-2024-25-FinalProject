package itt.lnc.news_aggregation_client.menu;

import itt.lnc.news_aggregation_client.dto.LoginRequest;
import itt.lnc.news_aggregation_client.dto.LoginResponse;
import itt.lnc.news_aggregation_client.dto.RegisterRequest;
import itt.lnc.news_aggregation_client.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class HomeMenu {

    private final AuthService authService;

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            System.out.println("1. Login");
            System.out.println(("2. Register"));
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    LoginRequest request = new LoginRequest();
                    System.out.println("Enter email: ");
                    request.setEmail(scanner.nextLine());
                    System.out.println("Enter password:");
                    request.setPassword(scanner.nextLine());
                    authService.login(request);
                    break;
                case 2:
                    RegisterRequest registerRequest = new RegisterRequest();
                    System.out.println("Enter username: ");
                    registerRequest.setUsername(scanner.nextLine());
                    System.out.println("Enter email: ");
                    registerRequest.setEmail(scanner.nextLine());
                    System.out.println("Enter password:");
                    registerRequest.setPassword(scanner.nextLine());
                    authService.register(registerRequest);
                    break;
            }
        }
    }
}
