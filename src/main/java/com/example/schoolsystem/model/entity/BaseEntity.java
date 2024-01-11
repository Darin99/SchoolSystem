package com.example.schoolsystem.model.entity;

import com.example.schoolsystem.model.commons.Course;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(name = "group_column", nullable = false)
    private String group;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Course course;
}
