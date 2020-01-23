package pl.raqiet.housing.cooperative.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.dao.AppUserRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Role;
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
        appUser.setPassword(PasswordUtils.hashPassword(appUser.getPassword()));
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
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listModerators() {
        return appUserRepository.findAllByRole(Role.MODERATOR);
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
