package com.babayan.service.currency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author by artbabayan
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @PrePersist
    protected void prePersist() {
        dateCreated = new Date();
    }

}
