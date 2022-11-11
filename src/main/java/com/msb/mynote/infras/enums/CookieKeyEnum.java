package com.msb.mynote.infras.enums;

import lombok.Getter;

@Getter
public enum CookieKeyEnum {

    TAGS_COOKIE("TAGS"),
    NOTES_COOKIE("NOTES"),
    NAME_COOKIE("NAME"),
    ;
    private String key;

    CookieKeyEnum(String key) {
        this.key = key;
    }

}
