package com.msb.mynote.infras.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Note {

    @Id
    private String _id;

    private String title;
    private String content;
    private List<String> tags;
    private String owner;

}
