package com.mellow.repository;

import com.mellow.model.ChatDao;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatDao, Long>{
}
