package com.whatsapp.backend.dao.repo;

import com.whatsapp.backend.dao.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<Account, String> {
}
