package pl.raqiet.housing.cooperative.api.service;

import org.springframework.security.access.annotation.Secured;
import pl.raqiet.housing.cooperative.domain.entity.Block;

import java.util.List;
import java.util.UUID;

public interface BlockService {
    @Secured("ROLE_ADMINISTRATOR")
    void addBlock(Block block);
    @Secured("ROLE_ADMINISTRATOR")
    void editBlock(Block block);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    List<Block> listAllBlocks();
    @Secured("ROLE_ADMINISTRATOR")
    void removeBlock(UUID id);
    @Secured({"ROLE_ADMINISTRATOR","ROLE_MODERATOR"})
    Block getBlock(UUID id);
}
