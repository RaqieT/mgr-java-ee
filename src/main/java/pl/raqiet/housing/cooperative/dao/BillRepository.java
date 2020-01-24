package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.raqiet.housing.cooperative.domain.entity.Bill;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findAllByOrderByCreationTimeAsc();
    @Query("select bi from Bill bi " +
            "join fetch bi.flat f " +
            "join fetch f.block b " +
            "join fetch b.moderators m " +
            "where m.username = :username " +
            "order by bi.creationTime asc")
    List<Bill> findAllByFlatBlockModeratorsContainingUserWithUsernameOrderByCreationTimeAsc(@Param("username") String username);
    List<Bill> findAllByFlatOwnerUsernameOrderByCreationTimeAsc(String username);
    Bill findByFlatOwnerUsernameAndCreationTimeIsAfter(String username, LocalDateTime time);
}
