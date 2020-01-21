package pl.raqiet.housing.cooperative.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.raqiet.housing.cooperative.domain.Role;

import java.util.Collection;
import java.util.Collections;

public final class AdministratorCall {
    private static final String ADMIN_ROLE = "ROLE_ADMINISTRATOR";

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
