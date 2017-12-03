package com.mellow.application.jpaservice.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }
}