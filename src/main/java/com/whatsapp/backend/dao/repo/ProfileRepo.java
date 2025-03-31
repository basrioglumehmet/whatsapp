package com.whatsapp.backend.dao.repo;

import com.whatsapp.backend.dao.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepo extends MongoRepository<Profile, String> {
}
