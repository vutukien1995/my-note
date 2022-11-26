package com.msb.mynote.service;

import com.msb.mynote.infras.model.Note;
import com.msb.mynote.infras.repository.NoteRepository;
import com.msb.mynote.rest.model.InputUpdateNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public String add (Note note) {
        if (!StringUtils.hasText(note.getTitle()))
            return "Title is empty !!";

        if (!StringUtils.hasText(note.getContent()))
            return "Content is empty !!";

        if (!StringUtils.hasText(note.getOwner()))
            return "Owner is empty !!";

        noteRepository.save(note);

        return "Added a new note !!!";
    }

    public String update(InputUpdateNote input) {
        if (!StringUtils.hasText(input.getId()))
            return "Id is empty !!";

        if (!StringUtils.hasText(input.getTitle()))
            return "Title is empty !!";

        if (!StringUtils.hasText(input.getContent()))
            return "Content is empty !!";

        Optional<Note> optionalNote = noteRepository.findById(input.getId());
        if(optionalNote.isEmpty())
            return "Note is empty !!";

        Note note = optionalNote.get();
        note.setTitle(input.getTitle());
        note.setContent(input.getContent());
        note.setTags(input.getTags());
        noteRepository.save(note);

        return "Updated a note !!!";
    }

    public Object getList(String userId, String tag) {
        if(!StringUtils.hasText(tag))
            return noteRepository.findByOwner(userId);

        return noteRepository.findByOwner(userId)
                .stream()
                .filter(line -> !CollectionUtils.isEmpty(line.getTags()) && line.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public Object get(String id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isEmpty())
            return "Note id is not exist !!!";

        return optionalNote.get();
    }

}
