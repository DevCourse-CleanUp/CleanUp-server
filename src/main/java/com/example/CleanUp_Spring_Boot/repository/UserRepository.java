package com.example.CleanUp_Spring_Boot.repository;

import com.example.CleanUp_Spring_Boot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT COUNT(u.email) FROM Users u WHERE u.email = :email")
    Integer findSameEmail(@Param("email") String email);

    @Query("SELECT COUNT(u.nickname) FROM Users u WHERE u.nickname = :nickname")
    Integer findSameNickname(@Param("nickname") String nickname);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findLoginUser(@Param("email") String email);

    @Query(value = "SELECT SUM(p.score) FROM Solves s LEFT JOIN Problems p ON s.problem_id = p.id WHERE s.user_id = :userId", nativeQuery = true)
    Integer calScore(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u SET u.total_score = :newTotalScore WHERE u.id = :userId", nativeQuery = true)
    void updateScore(@Param("newTotalScore") Integer newTotalScore, @Param("userId") Integer userId);
}
