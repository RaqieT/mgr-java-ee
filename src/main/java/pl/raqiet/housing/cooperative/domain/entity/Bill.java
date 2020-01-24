package pl.raqiet.housing.cooperative.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Bill extends BaseEntity {
    // TODO: describe
}
