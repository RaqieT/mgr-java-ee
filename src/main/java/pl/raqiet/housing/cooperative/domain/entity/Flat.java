package pl.raqiet.housing.cooperative.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Flat extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable=false)
    private Block block;

    @OneToOne(fetch = FetchType.EAGER)
    private AppUser owner;

    @OneToMany(mappedBy = "flat", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Bill> bills;

    private int number;
}
