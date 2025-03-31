package com.whatsapp.backend.dao.repo;

import com.whatsapp.backend.dao.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends MongoRepository<Account, String> {
   Optional<UserDetails> getAccountsByUsername(String username);
}
