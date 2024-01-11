package com.example.schoolsystem.model.commons;

import lombok.Getter;

@Getter
public enum Course {

    MAIN, SECONDARY;

    public static Course getCourse(String nameOfCourse) {
        return switch (nameOfCourse) {
            case "MAIN" -> MAIN;
            case "SECONDARY" -> SECONDARY;
            default -> throw new IllegalArgumentException("No such course");
        };
    }
}
