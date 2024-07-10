package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody Users user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body("이메일 형식이 잘못되었어요!");
        }
        if(userService.checkDuplication(user.getEmail(), user.getNickname())){
            return ResponseEntity.status(409).body("이메일 또는 닉네임이 이미 존재해요!");
        }
        Users newUser = userService.addNewUser(user);
        return ResponseEntity.status(201).body(newUser);
    }
}
