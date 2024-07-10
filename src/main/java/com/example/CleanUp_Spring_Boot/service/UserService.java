package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users addNewUser(Users user){
        return userRepository.save(user);
    }

    public Boolean checkDuplication(String email, String nickname){
        if(userRepository.findSameEmail(email) + userRepository.findSameNickname(nickname) > 0){
            return true;
        }
        return false;
    }


}