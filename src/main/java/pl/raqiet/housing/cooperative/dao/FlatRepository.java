package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.util.List;
import java.util.UUID;

public interface FlatRepository extends JpaRepository<Flat, UUID> {
    @Query("select f from Flat f " +
            "join fetch f.block b " +
            "join fetch b.moderators m " +
            "where m.username = :username " +
            "order by f.creationTime asc")
    List<Flat> findAllByBlockModeratorUsernameOrderByCreationTimeAsc(@Param("username") String username);
    List<Flat> findAllByOrderByCreationTimeAsc();
    Flat findByOwnerUsername(String username);
}
