package com.msb.mynote.service;

import com.msb.mynote.infras.constant.Constants;
import com.msb.mynote.infras.model.User;
import com.msb.mynote.infras.repository.UserRepository;
import com.msb.mynote.rest.model.InputCreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Object save(InputCreateUser inputCreateUser,
                       HttpServletResponse response) {
        if (!StringUtils.hasText(inputCreateUser.getName()))
            return "Name is empty !!!";

        // add cookie to response
        Cookie cookie = new Cookie(Constants.NAME_COOKIE, inputCreateUser.getName());
        response.addCookie(cookie);

        List<User> users = userRepository.findByName(inputCreateUser.getName());
        if(CollectionUtils.isEmpty(users)) {
            User user = new User();
            user.setName(inputCreateUser.getName());
            userRepository.save(user);
        }

        return "Created a User !!!";
    }

    public Object check(String name) {
        List<User> users = userRepository.findByName(name);

        if (!CollectionUtils.isEmpty(users))
            return users.get(0);
        else
            return null;
    }


}
