package com.example.CleanUp_Spring_Boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "solves")
public class Solves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problems problem;
}

