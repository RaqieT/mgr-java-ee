package pl.raqiet.housing.cooperative.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.dao.AppUserRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AuthUtils;
import pl.raqiet.housing.cooperative.util.PasswordUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    @NonNull
    private final AppUserRepository appUserRepository;

    @Override
    public void addAppUser(AppUser appUser) {
        appUser.setPassword(PasswordUtils.hashPassword(appUser.getPassword()));
        appUserRepository.save(appUser);
    }

    @Override
    public void editAppUser(AppUser appUser) {
        if (StringUtils.isEmpty(appUser.getPassword())) {
            appUser.setPassword(getAppUser(appUser.getId()).getPassword());
        } else {
            appUser.setPassword(PasswordUtils.hashPassword(appUser.getPassword()));
        }
        appUserRepository.save(appUser);
    }

    @Override
    public List<AppUser> listAppUser() {
        return appUserRepository.findAllByOrderByCreationTimeAsc();
    }

    @Override
    public void removeAppUser(UUID id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public AppUser getAppUser(UUID id) {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return appUserRepository.findById(id).orElse(null);
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            AppUser appUser = appUserRepository.findById(id).orElse(null);
            if (appUser == null) {
                return null;
            }
            if (appUser.getRole().equals(Role.LOCATOR)) {
                return appUser;
            }
        }

        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listLocatorsWithoutFlat() {
        return appUserRepository.findAllByRoleAndFlatIsNull(Role.LOCATOR);
    }

    @Override
    public List<AppUser> listLocatorsWithoutFlatAndFlatOwner(UUID flatOwner) {
        List<AppUser> appUsers = listLocatorsWithoutFlat();
        appUserRepository.findById(flatOwner).ifPresent(appUsers::add);
        return appUsers;
    }

    @Override
    public AppUser getMe() {
        return getAppUserByUsername(AuthUtils.getLoggedInUserUsername());
    }

    @Override
    public void register(AppUser appUser) {
        appUser.setRole(Role.LOCATOR);
        addAppUser(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getAppUserByUsername(s);
    }
}
