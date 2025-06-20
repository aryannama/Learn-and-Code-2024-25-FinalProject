package itt.lnc.news_aggregation_client.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
