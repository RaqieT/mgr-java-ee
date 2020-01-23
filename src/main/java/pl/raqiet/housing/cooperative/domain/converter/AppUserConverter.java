package pl.raqiet.housing.cooperative.domain.converter;

import org.springframework.core.convert.converter.Converter;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;

import java.util.UUID;

public class AppUserConverter implements Converter<String, AppUser> {
    private AppUserService appUserService;

    public AppUserConverter(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public AppUser convert(String s) {
        return appUserService.getAppUser(UUID.fromString(s));
    }
}
