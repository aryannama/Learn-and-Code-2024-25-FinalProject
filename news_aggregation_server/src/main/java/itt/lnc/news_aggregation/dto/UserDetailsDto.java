package itt.lnc.news_aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsDto {
    private String name;
    private String email;
    private String role;

}
