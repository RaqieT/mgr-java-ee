package pl.raqiet.housing.cooperative.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "blocks", fetch = FetchType.LAZY)
    private Set<AppUser> moderators = new HashSet<>();
    @OneToMany(mappedBy = "block", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Flat> flats = new HashSet<>();

    @Override
    public String toString() {
        return "Block{" +
                "address='" + address + '\'' +
                '}';
    }
}
