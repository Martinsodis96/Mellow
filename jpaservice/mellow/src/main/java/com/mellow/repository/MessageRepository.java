package com.mellow.repository;

import com.mellow.entity.model.MessageModel;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageModel, Long> {
}
