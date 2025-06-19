package iit.lnc.news_aggregation.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
