package com.example.CleanUp_Spring_Boot.repository;

import com.example.CleanUp_Spring_Boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
