package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.MessageModel;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageModel, Long> {
}
