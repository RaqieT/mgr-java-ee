package pl.raqiet.housing.cooperative.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class AppUser extends BaseEntity {
    @Column(name = "firstName", nullable = false)
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    @ManyToMany(mappedBy = "managers")
    private Set<Block> blocks = new HashSet<>();
}

