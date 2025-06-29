package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.LoginRequest;
import itt.lnc.news_aggregation.dto.LoginResponse;
import itt.lnc.news_aggregation.dto.RegisterRequest;

public interface AuthService {
     void register(RegisterRequest registerRequest);
     LoginResponse login(LoginRequest request);
}
