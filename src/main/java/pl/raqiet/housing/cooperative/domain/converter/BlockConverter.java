package pl.raqiet.housing.cooperative.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.domain.entity.Block;

import java.util.UUID;

public class BlockConverter implements Converter<String, Block> {
    private BlockService blockService;

    public BlockConverter(BlockService blockService) {
        this.blockService = blockService;
    }

    @Override
    public Block convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return blockService.getBlock(UUID.fromString(s));
    }
}
