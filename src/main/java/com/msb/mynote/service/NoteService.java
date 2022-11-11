package com.msb.mynote.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.model.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
public class NoteService {

    public String add (String cookieNote,
                       Note note,
                       HttpServletResponse response) {

        if(!StringUtils.hasText(note.getContent()))
            return "Content is empty !!!";

        List<Note> noteList = new ArrayList<>();
        if (StringUtils.hasText(cookieNote)) {
            byte[] result = Base64.getDecoder().decode(cookieNote);
            List<LinkedTreeMap> maps = new Gson().fromJson(new String(result), List.class);
            for (LinkedTreeMap linkedTreeMap: maps) {
                Note element = new Gson().fromJson(new Gson().toJson(linkedTreeMap), Note.class);
                noteList.add(element);
            }
        }

        note.setId(takeId(noteList));
        noteList.add(note);

        // add cookie to response
        String s = new Gson().toJson(noteList);
        log.info("cookie: " + s);
        String encodedString = Base64.getEncoder().encodeToString(s.getBytes());
        Cookie cookie = new Cookie(Constants.NOTES_COOKIE, encodedString);
        response.addCookie(cookie);

        return "Added a new note !!!";
    }

    public Object getList(String cookieNote) {
        List<Note> noteList = new ArrayList<>();
        if (StringUtils.hasText(cookieNote)) {
            byte[] result = Base64.getDecoder().decode(cookieNote);
            noteList = new Gson().fromJson(new String(result), List.class);
        }

        return noteList;
    }




    // ================================ PRIVATE FUNCTION ====================
    private int takeId(List<Note> noteList) {
        int id = 1;

        if(CollectionUtils.isEmpty(noteList))
            return id;

        for (Note note : noteList) {
            if (id < note.getId())
                id = note.getId();
        }

        return id + 1;
    }
}
