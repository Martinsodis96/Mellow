package com.mellow.repository;

import com.mellow.model.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long>{
}
