package itt.lnc.news_aggregation_client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.LoginRequest;
import itt.lnc.news_aggregation_client.dto.LoginResponse;
import itt.lnc.news_aggregation_client.dto.RegisterRequest;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public class AuthApiClient {

    public LoginResponse login(LoginRequest loginRequest) {
        HttpResponse<String> response = APIClient.post(Urls.LOGIN_URL, JsonParser.toJson(loginRequest), "");

        return JsonParser.parse(response.body(), new TypeReference<>() {
        });
    }

    public void register(RegisterRequest registerRequest) {
        HttpResponse<?> response = APIClient.post(Urls.REGISTER_URL, JsonParser.toJson(registerRequest), "");

        if (response.statusCode() == HttpStatus.CREATED.value()) {
            ConsoleUtil.printMessage("User Registered Successfully");
        }
    }
}
