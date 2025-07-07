package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.UserDetailsDto;

public interface UserService {
    UserDetailsDto getCurrentUser(Long userId);
}
