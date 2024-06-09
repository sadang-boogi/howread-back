package com.rebook.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false, columnDefinition = "datetime(6) default current_timestamp(6)")
    private ZonedDateTime createdAt;

    @Column(columnDefinition = "datetime(6) default current_timestamp(6)")
    private ZonedDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @Column(columnDefinition = "datetime(6)")
    private ZonedDateTime deletedAt;

    public boolean isDeleted() {
        return this.isDeleted;
    }

    @PrePersist
    public void PrePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void PreUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = ZonedDateTime.now();
    }
}
