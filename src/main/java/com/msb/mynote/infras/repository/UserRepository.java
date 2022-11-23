package com.msb.mynote.infras.repository;

import com.msb.mynote.infras.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name: '?0'}")
    public List<User> findByName(String name);

}
