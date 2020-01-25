package pl.raqiet.housing.cooperative.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.dao.BlockRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Block;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AuthUtils;
import pl.raqiet.housing.cooperative.util.HousingCooperativeException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        var user = SecurityContextHolder.getContext().getAuthentication();
        var loggedInUserAuths = user.getAuthorities();
        if (loggedInUserAuths.contains(Role.ADMINISTRATOR)) {
            return blockRepository.findAllByOrderByCreationTimeAsc();
        } else if (loggedInUserAuths.contains(Role.MODERATOR)) {
            return blockRepository.findAllByModeratorsUsername(user.getName());
        }
        throw new AccessDeniedException("No access for flat list");
    }

    @Override
    public void removeBlock(UUID id) {
        Optional<Block> byId = blockRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        Block block = byId.get();
        if (!block.getModerators().isEmpty()) {
            throw new HousingCooperativeException(String.format("Block has assigned moderators [%s]. Remove this block from them.",
                    block.getModerators().stream().map(AppUser::getUsername).collect(Collectors.joining(", "))));
        }

        if (!block.getFlats().isEmpty()) {
            throw new HousingCooperativeException(String.format("Block has assigned flats [%s]. Remove them first.\n",
                    block.getFlats().stream().map(f -> f.getBlock().getAddress() + "/" + f.getNumber())
                            .collect(Collectors.joining(", "))));
        }
        blockRepository.deleteById(id);
    }

    @Override
    public Block getBlock(UUID id) {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return blockRepository.findById(id).orElse(null);
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            Block block = blockRepository.findById(id).orElse(null);
            if (block == null) {
                return null;
            }
            if (block.getModerators().stream().map(AppUser::getUsername)
                    .anyMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                return block;
            }
        }

        throw new AccessDeniedException("Access is denied");
    }
}
