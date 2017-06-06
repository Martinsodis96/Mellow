package com.mellow.jpa.repository;

import com.mellow.jpa.entity.model.MessageModel;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageModel, Long> {
}
