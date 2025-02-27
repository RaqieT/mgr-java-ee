package pl.raqiet.housing.cooperative.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.domain.entity.Block;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface BlockRepository extends JpaRepository<Block, UUID> {
    List<Block> findAllByOrderByCreationTimeAsc();
    @Query("select b from Block b join fetch b.moderators m where m.username = :username")
    List<Block> findAllByModeratorsUsername(@Param("username") String username);
}
