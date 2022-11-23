package com.msb.mynote.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.infras.model.Tag;
import com.msb.mynote.infras.repository.NoteRepository;
import com.msb.mynote.infras.repository.TagRepository;
import com.msb.mynote.rest.model.InputCreateTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
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

    public Object getList(String owner) {
        return tagRepository.findByOwner(owner);
    }

    public Object getAll() {
        return tagRepository.findAll();
    }

    public Object delete(String cookieTags, String cookieNotes, String id, HttpServletResponse response) {

        List<Tag> tagList = new ArrayList<>();
        if (StringUtils.hasText(cookieTags)) {
            byte[] result = Base64.getDecoder().decode(cookieTags);
            List<LinkedTreeMap> maps = new Gson().fromJson(new String(result), List.class);
            for (LinkedTreeMap linkedTreeMap: maps) {
                Tag element = new Gson().fromJson(new Gson().toJson(linkedTreeMap), Tag.class);
                tagList.add(element);
            }
        }

        List<Note> noteList = new ArrayList<>();
        if (StringUtils.hasText(cookieNotes)) {
            byte[] result = Base64.getDecoder().decode(cookieNotes);
            List<LinkedTreeMap> maps = new Gson().fromJson(new String(result), List.class);
            for (LinkedTreeMap linkedTreeMap: maps) {
                Note element = new Gson().fromJson(new Gson().toJson(linkedTreeMap), Note.class);
                noteList.add(element);
            }
        }

        // add cookie to response
        String s = new Gson().toJson(tagList);
        log.info("Tags: " + s);
        String encodedString = Base64.getEncoder().encodeToString(s.getBytes());
        Cookie cookie = new Cookie(Constants.TAGS_COOKIE, encodedString);
        response.addCookie(cookie);

        // remove tag in notes
        for (Note note: noteList) {
            if (!CollectionUtils.isEmpty(note.getTags()))
                for (int i = 0; i<note.getTags().size(); i++) {
                    if (id.equals(note.getTags().get(i))) {
                        note.getTags().remove(i);
                    }
                }
        }

        // add cookie to response
        String sNote = new Gson().toJson(noteList);
        log.info("Notes: " + sNote);
        String encodedNotesString = Base64.getEncoder().encodeToString(sNote.getBytes());
        Cookie cookieNote = new Cookie(Constants.NOTES_COOKIE, encodedNotesString);
        response.addCookie(cookieNote);

        return "Deleted a note !!!";
    }


    // ================================ PRIVATE FUNCTION ====================

}
