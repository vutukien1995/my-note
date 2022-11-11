package com.msb.mynote.rest;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.enums.CookieKeyEnum;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.service.NoteService;
import com.msb.mynote.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class Subscribe {

    NoteService noteService = new NoteService();
    TagService tagService = new TagService();

    @RequestMapping(value = { "/hi" }, method = RequestMethod.GET)
    public String hi() {
        return "hi worlds !!!";
    }



    // ========================= NAME ============================

    @GetMapping(value = "/name/check")
    public Object checkName(@CookieValue(value = Constants.NAME_COOKIE, defaultValue = "") String cookieName) {
        log.info("name check: " + cookieName);
        if (!StringUtils.hasText(cookieName))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Guest Name is empty !!!");

        return cookieName;
    }

    @GetMapping(value = "/name/save")
    public String setName(@RequestParam(name = "name") String name,
                          HttpServletResponse response) {
        if (StringUtils.hasText(name))
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Guest Name is empty !!!");

        // add cookie to response
        Cookie cookie = new Cookie(Constants.NAME_COOKIE, name);
        response.addCookie(cookie);

        return "Set guest name successfully !!!";
    }



    // ========================= NOTES ===========================

    @GetMapping(value = "/note/getList")
    public Object getList(@CookieValue(value = Constants.NOTES_COOKIE, defaultValue = "") String cookieNote) {
        log.info("cookieNote: " + cookieNote);

        return noteService.getList(cookieNote);
    }

    @RequestMapping(value = { "/note/add" }, method = RequestMethod.POST)
    public Object addNote(@CookieValue(value = Constants.NOTES_COOKIE, defaultValue = "") String cookieNote,
                          HttpServletResponse response,
                          @RequestBody Note note) {
        log.info("note: " + new Gson().toJson(note));

        return noteService.add(cookieNote, note, response);
    }



    // ================== TAGS ===============

    @GetMapping(value = "/tag/getList")
    public Object getTagList(@CookieValue(value = Constants.TAGS_COOKIE, defaultValue = "") String cookieTag) {
        log.info("cookieTag: " + cookieTag);

        return tagService.getList(cookieTag);
    }

    @RequestMapping(value = { "/tag/add" }, method = RequestMethod.GET)
    public Object addTag(@CookieValue(value = Constants.TAGS_COOKIE, defaultValue = "") String cookieTag,
                          HttpServletResponse response,
                          @RequestParam(name = "tagName") String tagName) {
        log.info("tagName: " + tagName);

        return tagService.add(cookieTag, tagName, response);
    }

    @RequestMapping(value = { "/tag/{id}" }, method = RequestMethod.DELETE)
    public Object deleteTag(@CookieValue(value = Constants.NAME_COOKIE, defaultValue = "") String cookieName,
                          HttpServletResponse response,
                          @PathVariable String id) {
        log.info("delete tag: id " + id);

        String cookieTags = "";
        String cookieNotes = "";
//        return tagService.delete(cookieTags, cookieNotes, id, response);
        return "delete a tag";
    }

    // Test
    @GetMapping(value = "/getAllCookie")
    public Object getAllCookie(
            HttpServletRequest request
    ) {
        log.info("getAllCookie: ");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }

        log.info("cookies: " + cookies);
//        log.info("notesCookie: " + notesCookie);
//        log.info("tagsCookie: " + tagsCookie);

        return "getAllCookie !! ";
    }





}
