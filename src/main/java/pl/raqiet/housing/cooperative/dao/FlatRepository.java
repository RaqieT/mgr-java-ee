package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.time.LocalDateTime;
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
    @Query("select f from Flat f " +
            "where (select count(f1) from Flat f1 inner join f1.bills b where f1.id = f.id and b.registerTime > :startT and b.registerTime < :endT) = 0 AND f.owner is not null")
    List<Flat> findAllByBillsRegisterTimeNotBetweenAndOwnerNotNull(@Param("startT") LocalDateTime startT, @Param("endT") LocalDateTime endT);
}
