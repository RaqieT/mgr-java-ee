package pl.raqiet.housing.cooperative.api.service;

import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.util.List;
import java.util.UUID;

public interface FlatService {
    void addFlat(Flat Flat);
    void editFlat(Flat Flat);
    List<Flat> listAllFlats();
    void removeFlat(UUID id);
    Flat getFlat(UUID id);
}
