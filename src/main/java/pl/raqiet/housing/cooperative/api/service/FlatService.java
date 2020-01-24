package pl.raqiet.housing.cooperative.api.service;

import org.springframework.security.access.annotation.Secured;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.util.List;
import java.util.UUID;

@Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
public interface FlatService {
    void addFlat(Flat flat);
    void editFlat(Flat flat);
    List<Flat> listAllFlats();
    void removeFlat(UUID id);
    Flat getFlat(UUID id);
}
