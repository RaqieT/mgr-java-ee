package pl.raqiet.housing.cooperative.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.dao.FlatRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Flat;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AuthUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FlatServiceImpl implements FlatService {
    private final FlatRepository flatRepository;

    @Override
    public void addFlat(Flat flat) {
        editFlat(flat);
    }

    @Override
    public void editFlat(Flat flat) {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            flatRepository.save(flat);
            return;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (flat.getBlock().getModerators().stream().map(AppUser::getUsername)
                    .anyMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                flatRepository.save(flat);
                return;
            }
        }

        throw new AccessDeniedException("Cannot edit this flat");
    }

    @Override
    public List<Flat> listAllFlats() {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return flatRepository.findAllByOrderByCreationTimeAsc();
        } else if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            return flatRepository.findAllByBlockModeratorUsernameOrderByCreationTimeAsc(AuthUtils.getLoggedInUserUsername());
        }
        throw new AccessDeniedException("No access for flat list");
    }

    @Override
    public void removeFlat(UUID id) {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            flatRepository.deleteById(id);
            return;
        }

        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat == null) {
            return;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (flat.getOwner().getUsername().equals(AuthUtils.getLoggedInUserUsername())) {
                flatRepository.deleteById(id);
                return;
            }
        }

        throw new AccessDeniedException("Cannot remove this flat");
    }

    @Override
    public Flat getFlat(UUID id) {
        Flat flat = flatRepository.findById(id).orElse(null);
        if (flat == null) {
            return null;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return flat;
        } else if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (flat.getBlock().getModerators().stream().map(AppUser::getUsername)
                    .anyMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                return flat;
            }
        }

        throw new AccessDeniedException("No access to this flat");
    }

    @Override
    public Flat getFlatOfLoggedInUser() {
        return flatRepository.findByOwnerUsername(AuthUtils.getLoggedInUserUsername());
    }
}
