package com.msb.mynote.rest.controller;

import com.msb.mynote.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String note(Model model,
                       @RequestParam(required = false, defaultValue = "") String tag) {
        model.addAttribute("tag", tag);
        return "note";
    }

}
