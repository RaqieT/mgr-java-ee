package pl.raqiet.housing.cooperative.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.dao.AppUserRepository;
import pl.raqiet.housing.cooperative.domain.AppUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void addAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    @Override
    public void editAppUser(AppUser appUser) {
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
}
