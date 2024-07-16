package com.example.CleanUp_Spring_Boot.repository;

import com.example.CleanUp_Spring_Boot.entity.Solves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveRepository extends JpaRepository<Solves, Integer> {

    @Query("SELECT COUNT(s) > 0 FROM Solves s WHERE s.user.id = :userId AND s.problem.id = :problemId")
    boolean existsByUserIdAndProblemId(@Param("userId") Integer userId, @Param("problemId") Integer problemId);

    @Query("SELECT s.problem.id FROM Solves s WHERE s.user.id = :userId")
    List<Integer> findSolvedProblemsByUserId(@Param("userId") Integer userId);
}
