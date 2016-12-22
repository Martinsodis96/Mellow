package com.mellow.repository;

import com.mellow.model.ChatModel;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatModel, Long>{
}
