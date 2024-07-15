package com.example.CleanUp_Spring_Boot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Users {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(unique = true)
   @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
   private String email;

   private String password;

   @Column(unique = true)
   private String nickname;

   @Column(name = "total_score")
   private Integer totalScore = 0;

   public Users(String email, String password){
      this.email = email;
      this.password = password;
   }
   }
