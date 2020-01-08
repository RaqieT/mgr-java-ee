package pl.raqiet.housing.cooperative.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "created_date", updatable=false)
    @CreationTimestamp
    private Timestamp creationTime;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Timestamp modifiedDate;
}
