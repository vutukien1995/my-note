package com.msb.mynote.service;

import com.msb.mynote.infras.model.Note;
import com.msb.mynote.infras.model.Tag;
import com.msb.mynote.infras.repository.NoteRepository;
import com.msb.mynote.infras.repository.TagRepository;
import com.msb.mynote.rest.model.InputCreateTag;
import com.msb.mynote.rest.model.InputDeleteTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class TagService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    TagRepository tagRepository;

    public Object create(InputCreateTag input) {
        if(!StringUtils.hasText(input.getName()))
            return "Name is empty !!";

        if(!StringUtils.hasText(input.getOwner()))
            return "Owner is empty !!";

        Tag tag = new Tag();
        tag.setName(input.getName());
        tag.setOwner(input.getOwner());

        tagRepository.save(tag);

        return "Added a new tag !!!";
    }

    public Object update(InputCreateTag input) {
        if(!StringUtils.hasText(input.getName()))
            return "Name is empty !!";

        if(!StringUtils.hasText(input.getOwner()))
            return "Owner is empty !!";

        Tag tag = new Tag();
        tag.setName(input.getName());
        tag.setOwner(input.getOwner());

        tagRepository.save(tag);

        return "Added a new tag !!!";
    }

    public Object getList(String owner) {
        return tagRepository.findByOwner(owner);
    }

    public Object getAll() {
        return tagRepository.findAll();
    }

    public Object delete(InputDeleteTag input) {

        if(!StringUtils.hasText(input.getId()))
            return "Id is empty !!";

        if(!StringUtils.hasText(input.getOwner()))
            return "Owner is empty !!";

        List<Tag> tagList = tagRepository.findByOwner(input.getOwner());
        for(Tag tag: tagList)
            if(tag.get_id().equals(input.getId()))
                tagRepository.delete(tag);

        List<Note> noteList = noteRepository.findByOwner(input.getOwner());
        for (Note note: noteList) {
            if (!CollectionUtils.isEmpty(note.getTags())) {
                for (int i = 0; i<note.getTags().size(); i++) {
                    if (input.getId().equals(note.getTags().get(i))) {
                        note.getTags().remove(i);
                    }
                }
                noteRepository.save(note);
            }
        }

        return "Deleted a tag !!!";
    }

}
