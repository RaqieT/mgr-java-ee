package pl.raqiet.housing.cooperative.domain.entity;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends BaseEntity implements UserDetails {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String telephone;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moderator_block",
            joinColumns = @JoinColumn(name = "moderator_id"),
            inverseJoinColumns = @JoinColumn(name = "block_id")
    )
    private Set<Block> blocks = new HashSet<>(); // MODERATOR_ONLY

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Flat flat;

    @Override
    public Collection<Role> getAuthorities() {
        return new HashSet<>(Collections.singleton(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

