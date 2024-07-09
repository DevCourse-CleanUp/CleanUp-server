package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.entity.User;
import com.example.CleanUp_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class userController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @ResponseBody
    public List<User> login(){
        return userService.getUserApi();
    }
}
