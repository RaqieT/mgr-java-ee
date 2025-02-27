package pl.raqiet.housing.cooperative.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "created_date", updatable=false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime creationTime;

    @Column(name = "modified_date")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime modifiedDate;
}
