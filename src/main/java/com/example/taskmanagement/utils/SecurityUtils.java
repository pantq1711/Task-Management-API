package com.example.taskmanagement.utils;

import com.example.taskmanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public User getCurrentUser(){
        User user = new User();
        user.setId(2L);
        return user;
    }
}
