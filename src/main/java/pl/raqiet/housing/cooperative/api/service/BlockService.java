package pl.raqiet.housing.cooperative.api.service;

import pl.raqiet.housing.cooperative.domain.entity.Block;

import java.util.List;
import java.util.UUID;

public interface BlockService {
    void addBlock(Block block);
    void editBlock(Block block);
    List<Block> listAllBlocks();
    void removeBlock(UUID id);
    Block getBlock(UUID id);
}
