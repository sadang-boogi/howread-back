package com.rebook.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, columnDefinition = "datetime(6) default now()")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "datetime(6) default now() on update now()")
    private ZonedDateTime updatedAt;
}
