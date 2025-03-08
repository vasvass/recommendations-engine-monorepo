package com.vasvass.userprofileservice.repository;

import com.vasvass.userprofileservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    // Optionally, define custom queries if needed, e.g.:
    // List<User> findByUsername(String username);
}