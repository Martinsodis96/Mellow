package com.mellow.application.jpaservice.entity.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractModel {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
}