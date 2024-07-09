package com.example.CleanUp_Spring_Boot.service;

import com.example.CleanUp_Spring_Boot.entity.ProblemDetailProjection;
import com.example.CleanUp_Spring_Boot.entity.ProblemProjection;
import com.example.CleanUp_Spring_Boot.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public List<ProblemProjection> getProblemApi(){
        return problemRepository.findAllProjectedBy();
    }

    public Optional<ProblemDetailProjection> getDetailProblemApi(Integer id){
        return problemRepository.findProjectedById(id);
    }
}
