package com.mellow.jpa.repository;

import com.mellow.jpa.entity.model.ChatModel;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatModel, Long> {
}
