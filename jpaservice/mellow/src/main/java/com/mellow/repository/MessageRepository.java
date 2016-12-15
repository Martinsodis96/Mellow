package com.mellow.repository;

import com.mellow.model.MessageDao;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageDao, Long>{
}
