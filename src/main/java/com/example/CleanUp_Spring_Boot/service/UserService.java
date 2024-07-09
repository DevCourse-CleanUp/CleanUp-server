package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getUserApi(){
        return userRepository.findAll();
    }

}
