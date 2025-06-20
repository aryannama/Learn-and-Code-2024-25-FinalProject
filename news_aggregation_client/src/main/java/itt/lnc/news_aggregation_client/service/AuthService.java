package itt.lnc.news_aggregation_client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.LoginRequest;
import itt.lnc.news_aggregation_client.dto.LoginResponse;
import itt.lnc.news_aggregation_client.dto.RegisterRequest;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class AuthService {

    public String login(LoginRequest loginRequest) {
        HttpResponse<String> response = APIClient.post(Urls.LOGIN_URL, JsonParser.toJson(loginRequest));
        LoginResponse loginResponse = JsonParser.parse(response.body(), new TypeReference<>() {
        });

        if (response.statusCode() == 200) {
            System.out.println("Login Successful!");
            return loginResponse.getAccessToken();
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public void register(RegisterRequest registerRequest) {
        HttpResponse<?> response = APIClient.post(Urls.REGISTER_URL, JsonParser.toJson(registerRequest));

        if (response.statusCode() == 201) {
            System.out.println("User Registered Successfully");
        } else {
            throw new RuntimeException("Unable to create user!");
        }
    }
}
