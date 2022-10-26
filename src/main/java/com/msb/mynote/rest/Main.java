package com.msb.mynote.rest;

import com.google.gson.Gson;
import com.msb.mynote.infras.model.Note;
import com.msb.mynote.service.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Main {

    NoteService noteService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index2(Model model) {
        return "index";
    }

    @RequestMapping(value = { "/hello" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", "Hello world !!!");
        return "hello";
    }

    @RequestMapping(value = { "/note" }, method = RequestMethod.GET)
    public String note(Model model) {
        model.addAttribute("message", "Hello world !!!");
        return "note";
    }

}
