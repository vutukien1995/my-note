package com.msb.mynote.rest.controller;

import com.google.gson.Gson;
import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.rest.model.InputCreateTag;
import com.msb.mynote.rest.model.InputCreateUser;
import com.msb.mynote.rest.model.InputDeleteTag;
import com.msb.mynote.rest.model.InputUpdateNote;
import com.msb.mynote.service.NoteService;
import com.msb.mynote.service.TagService;
import com.msb.mynote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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

    @GetMapping(value = "/user/logout")
    public Object logOut(HttpServletRequest request,
                         HttpServletResponse response) {
        log.info("log out user");
        return userService.logOut(request, response);
    }



    // ========================= NOTES ===========================

    @GetMapping(value = "/note/getList")
    public Object getList(@RequestParam String userId,
                          @RequestParam(required = false, defaultValue = "") String tag) {
        return noteService.getList(userId, tag);
    }

    @GetMapping(value = "/note/{id}")
    public Object get(@PathVariable String id) {
        return noteService.get(id);
    }

    @RequestMapping(value = { "/note" }, method = RequestMethod.POST)
    public Object addNote(@RequestBody Note note) {
        log.info("note create: " + new Gson().toJson(note));
        return noteService.add(note);
    }

    @RequestMapping(value = { "/note" }, method = RequestMethod.PUT)
    public Object updateNote(@RequestBody InputUpdateNote input) {
        log.info("note update: " + new Gson().toJson(input));
        return noteService.update(input);
    }

    @RequestMapping(value = { "/note/{id}" }, method = RequestMethod.DELETE)
    public Object deleteNote(@PathVariable String id) {
        log.info("note delete: " + id);
        return noteService.delete(id);
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

    @DeleteMapping(value = { "/tag" })
    public Object deleteTag(@RequestBody InputDeleteTag input) {
        log.info("delete tag: " + new Gson().toJson(input));
        return tagService.delete(input);
    }




}
