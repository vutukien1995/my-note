package com.msb.mynote.rest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InputUpdateNote {

    private String id;
    private String title;
    private String content;
    private List<String> tags;

}
