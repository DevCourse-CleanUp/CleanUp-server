package com.example.CleanUp_Spring_Boot.repository;

import com.example.CleanUp_Spring_Boot.entity.ProblemDetailProjection;
import com.example.CleanUp_Spring_Boot.entity.ProblemProjection;
import com.example.CleanUp_Spring_Boot.entity.Problems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problems, Integer> {
    @Query("SELECT p.id as id, p.title as title, p.level as level, p.score as score FROM Problems p")
    List<ProblemProjection> findAllProjectedBy();

    @Query("SELECT p.id as id, p.title as title, p.level as level, p.score as score, p.answer as answer, p.content as content FROM Problems p WHERE p.id = :id")
    Optional<ProblemDetailProjection> findProjectedById(@Param("id") Integer id);
}
