package com.example.schoolsystem.service;

import org.openapitools.model.Course;
import org.openapitools.model.StudentDTO;

import java.util.List;

public interface StudentService {

    String getStudentsCount();

    List<StudentDTO> getStudentsByGroup(String group);

    List<StudentDTO> getStudentsByCourse(Course course);

    List<StudentDTO> getStudentsByGroupAndCourse(String group, Course course);

    List<StudentDTO> getStudentsOlderThanAndCourse(Integer age, Course course);

    void saveStudents(List<StudentDTO> studentDTO);

    void updateStudents(List<StudentDTO> studentDTO);

    void deleteStudentById(Long id);


}
