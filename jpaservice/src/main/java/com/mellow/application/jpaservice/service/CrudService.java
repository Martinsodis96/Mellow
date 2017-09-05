package com.mellow.application.jpaservice.service;

import com.mellow.application.jpaservice.entity.AbstractEntity;

import java.util.List;

public interface CrudService <E extends AbstractEntity> {

    List<E> getAll();

    E getById(Long id);

    E create(E entity);

    E update(E entity);

    E delete(E entity);
}
