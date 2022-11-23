package com.msb.mynote.rest.controller;

import com.google.gson.Gson;
import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.rest.model.InputCreateTag;
import com.msb.mynote.rest.model.InputCreateUser;
import com.msb.mynote.rest.model.InputDeleteTag;
import com.msb.mynote.service.NoteService;
import com.msb.mynote.service.TagService;
import com.msb.mynote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api")
public class Subscribe {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Autowired
    TagService tagService;



    // ========================= User ============================

    @GetMapping(value = "/user/check")
    public Object checkName(@CookieValue(value = Constants.NAME_COOKIE, defaultValue = "") String cookieName) {
        log.info("user check: " + cookieName);
        if (!StringUtils.hasText(cookieName))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Guest Name is empty !!!");
        return userService.check(cookieName);
    }

    @PostMapping(value = "/user/save")
    public Object saveName(@RequestBody InputCreateUser inputCreateUser,
                           HttpServletResponse response) {
        log.info("save user: " + new Gson().toJson(inputCreateUser));
        return userService.save(inputCreateUser, response);
    }



    // ========================= NOTES ===========================

    @GetMapping(value = "/note/getList/{name}")
    public Object getList(@PathVariable("name") String name) {
        return noteService.getList(name);
    }

    @RequestMapping(value = { "/note" }, method = RequestMethod.POST)
    public Object addNote(@RequestBody Note note) {
        log.info("note: " + new Gson().toJson(note));
        return noteService.add(note);
    }



    // ================== TAGS ===============

    @GetMapping(value = "/tag/getAll")
    public Object getAll() {
        return tagService.getAll();
    }

    @GetMapping(value = "/tag/getList/{user}")
    public Object getTagList(@PathVariable("user") String user) {
        log.info("getList tag: " + user);
        return tagService.getList(user);
    }

    @PostMapping(value = { "/tag" })
    public Object addTag(@RequestBody InputCreateTag input) {
        log.info("add tag: " + new Gson().toJson(input));
        return tagService.create(input);
    }

    @DeleteMapping(value = { "/tag/{id}" })
    public Object deleteTag(@RequestBody InputDeleteTag input) {
        log.info("delete tag: " + new Gson().toJson(input));

        String cookieTags = "";
        String cookieNotes = "";
//        return tagService.delete(cookieTags, cookieNotes, id, response);
        return "delete a tag";
    }




}
