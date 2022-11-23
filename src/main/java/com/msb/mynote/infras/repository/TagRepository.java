package com.msb.mynote.infras.repository;

import com.msb.mynote.infras.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TagRepository extends MongoRepository<Tag, String> {

    @Query("{owner: '?0'}")
    public List<Tag> findByOwner(String owner);

}
