package pl.raqiet.housing.cooperative.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.raqiet.housing.cooperative.domain.Const;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Bill extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Flat flat;

    private boolean approved = false;

    private double gasUsage; // liter
    private double powerUsage; // kWh
    private double waterUsage; // liter

    public double getGasPrice() {
        return gasUsage * Const.ReadingsPrices.GAS_LITER_PRICE;
    }

    public double getPowerUsage() {
        return powerUsage * Const.ReadingsPrices.POWER_KWH_PRICE;
    }

    public double getWaterUsage() {
        return waterUsage * Const.ReadingsPrices.WATER_LITER_PRICE;

    }
}
