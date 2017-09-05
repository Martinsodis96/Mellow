package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
}
