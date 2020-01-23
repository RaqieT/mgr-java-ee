package pl.raqiet.housing.cooperative.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Flat extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable=false)
    @EqualsAndHashCode.Exclude
    private Block block;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private AppUser owner;

    private int number;
}
