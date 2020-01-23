package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.raqiet.housing.cooperative.domain.entity.BaseEntity;

import java.util.UUID;

public interface BaseEntityRepository extends JpaRepository<BaseEntity, UUID> {
}
