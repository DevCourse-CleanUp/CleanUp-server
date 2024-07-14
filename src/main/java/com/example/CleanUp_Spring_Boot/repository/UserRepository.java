package com.example.CleanUp_Spring_Boot.repository;

import com.example.CleanUp_Spring_Boot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT COUNT(p.email) FROM Users p WHERE p.email = :email")
    Integer findSameEmail(@Param("email") String email);

    @Query("SELECT COUNT(p.nickname) FROM Users p WHERE p.nickname = :nickname")
    Integer findSameNickname(@Param("nickname") String nickname);

    @Query("SELECT p FROM Users p WHERE p.email = :email")
    Users findLoginUser(@Param("email") String email);
}
