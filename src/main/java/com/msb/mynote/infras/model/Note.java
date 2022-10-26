package com.msb.mynote.infras.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Note {

    private int id;
    private String title;
    private String content;
    private List<String> tags;

}
