package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.auth.JwtUtil;
import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${jwtExpiredMs}")
    private String jwtExpiredMs;

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

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> loginMap){
        Users checkUserInform = userService.findUser(loginMap.get("email"));
        if(checkUserInform != null){
            if (userService.checkPassword(loginMap.get("password"), checkUserInform.getPassword())) {
                // jwt 토큰 발행 코드 부분
                String jwtToken = JwtUtil.createJwt(loginMap.get("password"),jwtSecretKey, jwtExpiredMs);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + jwtToken);

                return new ResponseEntity<>("로그인에 성공하였습니다!", headers, HttpStatus.OK);
            }
            else{
                return ResponseEntity.status(400).body("틀린 비밀번호 입니다.");
            }
        }else{
            return ResponseEntity.status(400).body("존재하지 않는 이메일 입니다.");
        }
    }

    @ResponseBody
    @PostMapping("/jwtTest")
    public String jwtTest(){
        return "jwt 테스트 페이지입니다.";
    }
}