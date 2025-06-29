package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.LoginRequest;
import itt.lnc.news_aggregation.dto.LoginResponse;
import itt.lnc.news_aggregation.dto.RegisterRequest;
import itt.lnc.news_aggregation.enums.UserRole;
import itt.lnc.news_aggregation.exception.BadCredentialsException;
import itt.lnc.news_aggregation.exception.UserAlreadyExistsException;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.security.JwtService;
import itt.lnc.news_aggregation.service.AuthService;
import itt.lnc.news_aggregation.service.NotificationConfigurationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final NotificationConfigurationService notificationConfigurationService;

    public void register(RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + registerRequest.getEmail());
        }

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User registerUser = modelMapper.map(registerRequest, User.class);
        registerUser.setRole(UserRole.USER);
        registerUser = userRepository.save(registerUser);
        notificationConfigurationService.createDefaultNotificationConfigurations(registerUser);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid Credentials!");
        }

        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtService.generateToken(request.getEmail()));
        return response;
    }
}
