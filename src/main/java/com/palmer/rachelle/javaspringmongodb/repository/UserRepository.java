package com.palmer.rachelle.javaspringmongodb.repository;

import com.palmer.rachelle.javaspringmongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByEmailStartsWithOrName(String email, String name);
}
