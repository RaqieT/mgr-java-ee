package pl.raqiet.housing.cooperative.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @ToString.Exclude
    @ManyToMany(mappedBy = "blocks", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AppUser> moderators = new HashSet<>();

    @OneToMany(mappedBy = "block", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Flat> flats = new HashSet<>();
}
