package itt.lnc.news_aggregation.security;

import itt.lnc.news_aggregation.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ((User) authentication.getPrincipal()).getId();
        }
        return null;
    }
}
