package com.example.CleanUp_Spring_Boot.controller;

import com.example.CleanUp_Spring_Boot.auth.JwtUtil;
import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.service.SolveService;
import com.example.CleanUp_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SolveController {

    @Autowired
    private SolveService solveService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @PostMapping("/solves")
    public ResponseEntity<String> solveProblem(@RequestParam("problem_id") Integer problemId, @RequestHeader("Authorization") String jwt) {
        String email;
        try {
            email = jwtUtil.getEmailFromJwt(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("잘못된 토큰입니다.");
        }

        Users user = userService.findUser(email);
        if (user == null) {
            return ResponseEntity.status(404).body("존재하지 않는 사용자입니다.");
        }

        try {
            solveService.saveSolve(user.getId(), problemId);
            return ResponseEntity.ok("성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/solves")
    public ResponseEntity<List<Integer>> getSolvedProblems( @RequestHeader("Authorization") String jwt) {

        String email;
        try {
            email = jwtUtil.getEmailFromJwt(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }

        Users user = userService.findUser(email);
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }

        List<Integer> solvedProblems = solveService.getSolvedProblemsByUserId(user.getId());
        return ResponseEntity.ok(solvedProblems);
    }
}
