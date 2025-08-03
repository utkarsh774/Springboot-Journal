package com.example.journalApp.Repository;

import com.example.journalApp.Model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel,String> {
    Optional<UserModel> findByEmail(String email);
}
