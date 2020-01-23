package pl.raqiet.housing.cooperative.init;

import org.springframework.stereotype.Component;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AdministratorCall;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final AppUserService appUserService;

    public DataInitializer(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostConstruct
    public void init() {
        AdministratorCall.call(() -> {
            if (appUserService.getAppUserByUsername("admin") != null) {
                return;
            }

            appUserService.addAppUser(AppUser.builder()
                .username("admin")
                .password("admin")
                .email("admin@admin")
                .firstName("admin")
                .lastName("admin")
                .role(Role.ADMINISTRATOR).build());

        });
    }
}
