package pl.raqiet.housing.cooperative.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.dao.BlockRepository;
import pl.raqiet.housing.cooperative.domain.entity.Block;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Unauthenticated");
        }
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
