package com.example.schoolsystem.repository;

import com.example.schoolsystem.model.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO implements BaseEntityDAO<Student> {

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }
}