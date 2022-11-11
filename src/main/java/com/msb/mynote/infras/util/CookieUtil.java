package com.msb.mynote.infras.util;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.msb.mynote.infras.model.Note;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CookieUtil {

//    public static <T> List<T> getList(String cookie) {
//        List<T> list = new ArrayList<>();
//        if (StringUtils.hasText(cookie)) {
//            byte[] result = Base64.getDecoder().decode(cookie);
//            List<LinkedTreeMap> maps = new Gson().fromJson(new String(result), List.class);
//            for (LinkedTreeMap linkedTreeMap: maps) {
//                T element = new Gson().fromJson(new Gson().toJson(linkedTreeMap), clazz);
//                list.add(element);
//            }
//        }
//
//        return list;
//    }

}
