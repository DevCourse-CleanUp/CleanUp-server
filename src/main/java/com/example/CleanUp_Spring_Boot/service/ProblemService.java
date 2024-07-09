package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.entity.Problems;
import com.example.CleanUp_Spring_Boot.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public List<Problems> getProblemApi(){
        return problemRepository.findAll();
    }

}
