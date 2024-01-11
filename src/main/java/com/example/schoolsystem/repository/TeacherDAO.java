package com.example.schoolsystem.repository;

import com.example.schoolsystem.model.entity.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO implements BaseEntityDAO<Teacher> {

    @Override
    public Class<Teacher> getEntityClass() {
        return Teacher.class;
    }
}