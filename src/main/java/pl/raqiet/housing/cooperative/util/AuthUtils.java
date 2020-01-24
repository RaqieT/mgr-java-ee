package pl.raqiet.housing.cooperative.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.raqiet.housing.cooperative.domain.entity.Role;

import java.util.Optional;

public class AuthUtils {
    public static boolean isLoggedInUserInRole(Role role) {
        var user = SecurityContextHolder.getContext().getAuthentication();
        if (user == null) {
            throw new AccessDeniedException("No user context");
        }
        return user.getAuthorities().contains(role);
    }

    public static String getLoggedInUserUsername() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        if (user == null) {
            throw new AccessDeniedException("No user context");
        }
        return user.getName();
    }
}
