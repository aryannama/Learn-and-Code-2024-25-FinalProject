package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.UserDetailsDto;
import itt.lnc.news_aggregation.exception.UserNotExistException;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsDto getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User not found"));

        return new UserDetailsDto(user.getName(), user.getEmail(), user.getRole().name());
    }
}
