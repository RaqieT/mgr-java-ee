package pl.raqiet.housing.cooperative.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Block extends BaseEntity {
    @Column(unique = true)
    private String address;
    @ManyToMany
    @JoinTable(name = "block_flat",
            joinColumns = @JoinColumn(name = "block_id"),
            inverseJoinColumns = @JoinColumn(name = "flat_id")
    )
    private Set<AppUser> managers = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "block", fetch = FetchType.LAZY)
    private Set<Flat> flats = new HashSet<>();
}
