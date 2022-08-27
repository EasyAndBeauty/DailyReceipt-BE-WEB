package com.sprint.dailyreceipt.domain.common;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(updatable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
