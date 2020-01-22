package pl.raqiet.housing.cooperative.api.service;

import org.springframework.security.access.annotation.Secured;
import pl.raqiet.housing.cooperative.domain.AppUser;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    @Secured("ROLE_ADMINISTRATOR")
    void addAppUser(AppUser appUser);
    @Secured("ROLE_ADMINISTRATOR")
    void editAppUser(AppUser appUser);
    @Secured("ROLE_ADMINISTRATOR")
    List<AppUser> listAppUser();
    @Secured("ROLE_ADMINISTRATOR")
    void removeAppUser(UUID uuid);
    @Secured("ROLE_ADMINISTRATOR")
    AppUser getAppUser(UUID uuid);
    @Secured("ROLE_ADMINISTRATOR")
    AppUser getAppUserByUsername(String username);
    void register(AppUser appUser);
}
