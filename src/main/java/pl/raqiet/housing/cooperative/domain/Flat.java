package pl.raqiet.housing.cooperative.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Flat extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="block_id", nullable=false)
    private Block block;
    private int number;
}
