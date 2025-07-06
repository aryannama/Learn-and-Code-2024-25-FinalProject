package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.UserDetailsDto;
import itt.lnc.news_aggregation.security.SecurityContext;
import itt.lnc.news_aggregation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDto> getCurrentUser() {
        Long userId = SecurityContext.getCurrentUserId();
        return ResponseEntity.ok(userService.getCurrentUser(userId));
    }

}
