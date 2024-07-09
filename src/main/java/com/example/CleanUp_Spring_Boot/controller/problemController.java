package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.entity.Problems;
import com.example.CleanUp_Spring_Boot.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class problemController {
    @Autowired
    private ProblemService problemService;


    @GetMapping("/problemset")
    @ResponseBody
    public List<Problems> problemset() {
        return problemService.getProblemApi();
    }

}
