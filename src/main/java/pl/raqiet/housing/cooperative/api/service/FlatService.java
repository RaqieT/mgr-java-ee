package pl.raqiet.housing.cooperative.api.service;

import org.springframework.security.access.annotation.Secured;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.util.List;
import java.util.UUID;

public interface FlatService {
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void addFlat(Flat flat);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void editFlat(Flat flat);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    List<Flat> listAllFlats();
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void removeFlat(UUID id);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    Flat getFlat(UUID id);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR", "ROLE_LOCATOR"})
    Flat getFlatOfLoggedInUser();
    List<Flat> getNotBilledFlatsOfPreviousMonth();
}
