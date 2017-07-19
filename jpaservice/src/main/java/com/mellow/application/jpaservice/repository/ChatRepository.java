package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.ChatModel;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatModel, Long> {
}
