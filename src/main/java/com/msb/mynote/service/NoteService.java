package com.msb.mynote.service;

import com.msb.mynote.infras.model.Note;
import com.msb.mynote.infras.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public String add (Note note) {
        if (!StringUtils.hasText(note.getTitle()))
            return "Title is empty !!";

        if (!StringUtils.hasText(note.getContent()))
            return "Title is empty !!";

        if (!StringUtils.hasText(note.getOwner()))
            return "Owner is empty !!";

        noteRepository.save(note);

        return "Added a new note !!!";
    }

    public Object getList(String userId) {
        log.info("getList note: " + userId);

        return noteRepository.findByOwner(userId);
    }



    // ================================ PRIVATE FUNCTION ====================

}
