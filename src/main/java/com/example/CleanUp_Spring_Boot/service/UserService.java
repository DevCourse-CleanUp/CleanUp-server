package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.auth.JwtUtil;
import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Users addNewUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Boolean checkDuplication(String email, String nickname){
        if(userRepository.findSameEmail(email) + userRepository.findSameNickname(nickname) > 0){
            return true;
        }
        return false;
    }

    public Users findUser(String email){
        return userRepository.findLoginUser(email);
    }

    public boolean checkPassword(String inputPassword, String DBPassword){
        if(passwordEncoder.matches(inputPassword, DBPassword)){
            return true;
        }
        return false;
    }
}