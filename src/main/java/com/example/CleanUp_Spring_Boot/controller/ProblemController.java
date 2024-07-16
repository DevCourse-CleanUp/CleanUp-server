package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.entity.ProblemDetailProjection;
import com.example.CleanUp_Spring_Boot.entity.ProblemProjection;
import com.example.CleanUp_Spring_Boot.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller

public class ProblemController {
    @Autowired
    private ProblemService problemService;


    @GetMapping("/problemset")
    @ResponseBody
    public List<ProblemProjection> problemset() {
        return problemService.getProblemApi();
    }

    @GetMapping("/problemset/{id}")
    @ResponseBody
    public Optional<ProblemDetailProjection> detailproblem(@PathVariable("id") Integer id) {
        return problemService.getDetailProblemApi(id);
    }

}
