package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.UserApiClient;
import itt.lnc.news_aggregation_client.dto.UserDetailsDto;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserApiClient userApiClient;

    public void setCurrentUser() {
        UserDetailsDto userDetailsDto = userApiClient.getCurrentUser();
        SessionManager.setName(userDetailsDto.getName());
        SessionManager.setEmail(userDetailsDto.getEmail());
        SessionManager.setRole(userDetailsDto.getRole());
    }
}
