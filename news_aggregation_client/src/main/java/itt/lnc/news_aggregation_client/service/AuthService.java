package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.AuthApiClient;
import itt.lnc.news_aggregation_client.dto.LoginRequest;
import itt.lnc.news_aggregation_client.dto.LoginResponse;
import itt.lnc.news_aggregation_client.dto.RegisterRequest;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthApiClient authApiClient;

    public void login(LoginRequest loginRequest) {
        LoginResponse response = authApiClient.login(loginRequest);
        SessionManager.setAccessToken(response.getAccessToken());
    }

    public void register(RegisterRequest registerRequest) {
        authApiClient.register(registerRequest);
    }
}
