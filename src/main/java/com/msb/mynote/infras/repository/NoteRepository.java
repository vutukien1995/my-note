package com.msb.mynote.infras.repository;

import com.msb.mynote.infras.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    @Query("{owner: '?0'}")
    public List<Note> findByOwner(String owner);

}
