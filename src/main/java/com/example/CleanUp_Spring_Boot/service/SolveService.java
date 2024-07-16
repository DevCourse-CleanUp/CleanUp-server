package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.entity.Problems;
import com.example.CleanUp_Spring_Boot.entity.Solves;
import com.example.CleanUp_Spring_Boot.entity.Users;
import com.example.CleanUp_Spring_Boot.repository.ProblemRepository;
import com.example.CleanUp_Spring_Boot.repository.SolveRepository;
import com.example.CleanUp_Spring_Boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolveService {

    @Autowired
    private SolveRepository solveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemRepository problemRepository;

    public void saveSolve(Integer userId, Integer problemId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        Problems problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("존재하지 않는 문제입니다."));

        if (solveRepository.existsByUserIdAndProblemId(userId, problemId)) {
            throw new RuntimeException("이미 푼 문제입니다.");
        }

        Solves solve = new Solves();
        solve.setUser(user);
        solve.setProblem(problem);

        solveRepository.save(solve);
    }
    public List<Integer> getSolvedProblemsByUserId(Integer userId) {
        return solveRepository.findSolvedProblemsByUserId(userId);
    }
}
