package ru.pfr.contracts2.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class AuditEntity {

    @LastModifiedDate
    private LocalDateTime date_update;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime date_create;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

}
