package com.example.CleanUp_Spring_Boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Problems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String answer;
    private String level;
    private Integer score;

    @Embedded
    private Content content;

    @Data
    @Embeddable
    public static class Content {
        @Column(name = "description")
        private String description;
        @Column(name = "code")
        private String code;
    }

}
