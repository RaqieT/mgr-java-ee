package pl.raqiet.housing.cooperative.api.service;

import pl.raqiet.housing.cooperative.domain.AppUser;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    void addAppUser(AppUser appUser);
    void editAppUser(AppUser appUser);
    List<AppUser> listAppUser();
    void removeAppUser(UUID uuid);
    AppUser getAppUser(UUID uuid);
}
