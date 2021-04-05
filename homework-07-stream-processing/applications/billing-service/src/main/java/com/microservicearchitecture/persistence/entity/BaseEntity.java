package com.microservicearchitecture.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @PreUpdate
    void beforeUpdate() {
        updatedAt = Instant.now();
    }
}
