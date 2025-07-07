package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.LoginRequest;
import itt.lnc.news_aggregation.dto.LoginResponse;
import itt.lnc.news_aggregation.dto.RegisterRequest;
import itt.lnc.news_aggregation.enums.UserRole;
import itt.lnc.news_aggregation.exception.BadCredentialsException;
import itt.lnc.news_aggregation.exception.UserAlreadyExistsException;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.UserRepository;
import itt.lnc.news_aggregation.security.JwtService;
import itt.lnc.news_aggregation.service.implementations.DefaultAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private NotificationConfigurationService notificationConfigurationService;

    @InjectMocks
    private DefaultAuthService authService;

    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("testuser", "test@example.com", "password123");
        user = User.builder()
                .email("test@example.com")
                .password("encodedPass")
                .role(UserRole.USER)
                .build();
    }

    @Test
    void register_shouldSaveUserAndCreateNotificationConfig() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPass");
        when(modelMapper.map(registerRequest, User.class)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        authService.register(registerRequest);

        verify(userRepository).save(any(User.class));
        verify(notificationConfigurationService).createDefaultNotificationConfigurations(user);
    }

    @Test
    void register_shouldThrowExceptionIfUserExists() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnAccessToken() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(loginRequest.getEmail())).thenReturn("dummy-jwt-token");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("dummy-jwt-token", response.getAccessToken());
    }

    @Test
    void login_shouldThrowBadCredentialsIfNotAuthenticated() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
    }
}
