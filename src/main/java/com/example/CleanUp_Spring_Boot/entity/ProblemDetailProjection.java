package com.example.CleanUp_Spring_Boot.entity;

public interface ProblemDetailProjection {
    Integer getId();
    String getTitle();
    String getLevel();
    Integer getScore();
    String getAnswer();
    ContentProjection getContent();

    interface ContentProjection {
        String getDescription();
        String getCode();
    }
}
