package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Role;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    List<AppUser> findAllByOrderByCreationTimeAsc();
    List<AppUser> findByLastName(String lastName);
    AppUser findByUsername(String username);
    List<AppUser> findAllByRoleAndFlatIsNull(Role role);
}
