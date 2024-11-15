package com.sachin.repository;

import com.sachin.entity.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;


public interface UserRepository extends MongoRepository<UserLogin, String> {
    Optional<UserLogin> findByUsername(String username);
}
