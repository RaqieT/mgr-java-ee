package pl.raqiet.housing.cooperative.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.raqiet.housing.cooperative.domain.entity.Role;

import java.util.Collections;

public final class AdministratorCall {
    private static final String ADMIN_ROLE = "ADMINISTRATOR";

    public static void call(Runnable method) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                ADMIN_ROLE,
                Collections.singleton(Role.ADMINISTRATOR)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        method.run();
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
