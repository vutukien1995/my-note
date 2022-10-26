package com.msb.mynote.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.infras.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class TagService {

    public Object add(String cookieTags, String tagName, HttpServletResponse response) {
        if(!StringUtils.hasText(tagName))
            return "Tag is empty !!!";

        List<Tag> tagList = new ArrayList<>();
        if (StringUtils.hasText(cookieTags)) {
            byte[] result = Base64.getDecoder().decode(cookieTags);
            List<LinkedTreeMap> maps = new Gson().fromJson(new String(result), List.class);
            for (LinkedTreeMap linkedTreeMap: maps) {
                Tag element = new Gson().fromJson(new Gson().toJson(linkedTreeMap), Tag.class);
                tagList.add(element);
            }
        }

        Tag newTag = new Tag();
        newTag.setId(takeId(tagList));
        newTag.setName(tagName);
        tagList.add(newTag);

        // add cookie to response
        String s = new Gson().toJson(tagList);
        log.info("cookie: " + s);
        String encodedString = Base64.getEncoder().encodeToString(s.getBytes());
        Cookie cookie = new Cookie("tags", encodedString);
        response.addCookie(cookie);

        return "Added a new note !!!";
    }

    public Object getList(String cookieTags) {
        List<Tag> noteList = new ArrayList<>();
        if (StringUtils.hasText(cookieTags)) {
            byte[] result = Base64.getDecoder().decode(cookieTags);
            noteList = new Gson().fromJson(new String(result), List.class);
        }

        return noteList;
    }


    // ================================ PRIVATE FUNCTION ====================
    private int takeId(List<Tag> tagList) {
        int id = 1;

        if(CollectionUtils.isEmpty(tagList))
            return id;

        for (Tag tag : tagList) {
            if (id < tag.getId())
                id = tag.getId();
        }

        return id + 1;
    }

}
