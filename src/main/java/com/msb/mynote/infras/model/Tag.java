package com.msb.mynote.infras.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Tag {

    @Id
    private String _id;

    private String name;
    private String owner;

}
