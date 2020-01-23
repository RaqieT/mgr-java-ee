package pl.raqiet.housing.cooperative.domain.converter;

import org.springframework.core.convert.converter.Converter;
import pl.raqiet.housing.cooperative.domain.entity.Role;

public class RoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String s) {
        return Role.valueOf(s);
    }
}
