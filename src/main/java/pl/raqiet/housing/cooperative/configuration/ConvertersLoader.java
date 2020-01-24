package pl.raqiet.housing.cooperative.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.api.service.BlockService;
import pl.raqiet.housing.cooperative.domain.converter.AppUserConverter;
import pl.raqiet.housing.cooperative.domain.converter.BlockConverter;
import pl.raqiet.housing.cooperative.domain.converter.RoleConverter;

@Configuration
@RequiredArgsConstructor
public class ConvertersLoader {
    private final AppUserService appUserService;
    private final BlockService blockService;

    @Bean
    public RoleConverter roleConverter() {
        return new RoleConverter();
    }

    @Bean
    public AppUserConverter appUserConverter() {
        return new AppUserConverter(appUserService);
    }

    @Bean
    public BlockConverter blockConverter() {
        return new BlockConverter(blockService);
    }
}
