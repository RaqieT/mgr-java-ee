package pl.raqiet.housing.cooperative.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.dao.BlockRepository;
import pl.raqiet.housing.cooperative.domain.Block;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {
    @NonNull
    private final BlockRepository blockRepository;

    @Override
    public void addBlock(Block block) {
        blockRepository.save(block);
    }

    @Override
    public void editBlock(Block block) {
        blockRepository.save(block);
    }

    @Override
    public List<Block> listAllBlocks() {
        return blockRepository.findAllByOrderByCreationTimeAsc();
    }

    @Override
    public void removeBlock(UUID id) {
        blockRepository.deleteById(id);
    }

    @Override
    public Block getBlock(UUID id) {
        return blockRepository.findById(id).orElse(null);
    }
}
