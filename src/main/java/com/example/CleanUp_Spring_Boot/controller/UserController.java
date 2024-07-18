package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.auth.JwtUtil;
import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody Users user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 형식이 잘못되었어요!");
        }
        if(userService.checkDuplication(user.getEmail(), user.getNickname())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 또는 닉네임이 이미 존재해요!");
        }
        Users newUser = userService.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> loginMap){
        Users checkUserInform = userService.findUser(loginMap.get("email"));
        if(checkUserInform != null){
            if (userService.checkPassword(loginMap.get("password"), checkUserInform.getPassword())) {
                // jwt 토큰 발행
                String jwtToken = jwtUtil.createJwt(loginMap.get("email"));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", jwtToken);

                return new ResponseEntity<>("로그인에 성공하였습니다!", headers, HttpStatus.OK);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("틀린 비밀번호 입니다.");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 이메일 입니다.");
        }
    }

    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    @GetMapping("/user")
    public ResponseEntity user(@RequestHeader("Authorization") String jwt){
        String userEmail = jwtUtil.getEmailFromJwt(jwt);
        Users checkUserInform = userService.findUser(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(checkUserInform);
    }

    @PutMapping("/solves")
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    public ResponseEntity updateTotalScore(@RequestHeader("Authorization") String jwt){
        String userEmail = jwtUtil.getEmailFromJwt(jwt);
        userService.calTotalScore(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body("점수가 최신화되었습니다!");
    }

    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity ranking(){
        List<Users> TOP3 = userService.checkTOP3();
        return ResponseEntity.status(HttpStatus.OK).body(TOP3);
    }
}