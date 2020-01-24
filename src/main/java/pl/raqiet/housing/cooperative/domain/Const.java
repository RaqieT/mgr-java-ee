package pl.raqiet.housing.cooperative.domain;

import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class Const {
    public static class ReadingsPrices {
        public static final double WATER_LITER_PRICE = getEnv("housing.cooperative.water.liter.price",
                Double::parseDouble).orElse(1d);
        public static final double GAS_LITER_PRICE = getEnv("housing.cooperative.gas.liter.price",
                Double::parseDouble).orElse(1d);
        public static final double POWER_KWH_PRICE = getEnv("housing.cooperative.power.kwh.price",
                Double::parseDouble).orElse(0.5d);

    }

    public static class ReadingsDefaultValues {
        public static final double WATER_VALUE = getEnv("housing.cooperative.water.default.value",
                Double::parseDouble).orElse(10d);
        public static final double GAS_VALUE = getEnv("housing.cooperative.gas.default.value",
                Double::parseDouble).orElse(20d);
        public static final double POWER_VALUE = getEnv("housing.cooperative.power.default.value",
                Double::parseDouble).orElse(30d);
    }

    private static <T> Optional<T> getEnv(String key, Function<String, T> stringParser) {
        String getenv = System.getenv(key);
        if (StringUtils.isEmpty(getenv)) {
            return Optional.empty();
        }
        return Optional.of(stringParser.apply(getenv));
    }
}
