package com.example.schoolsystem.service;

import org.openapitools.model.Course;
import org.openapitools.model.TeacherDTO;

import java.util.List;

public interface TeacherService {

    String getTeachersCount();

    List<TeacherDTO> getTeachersByGroup(String group);

    List<TeacherDTO> getTeachersByCourse(Course course);

    List<TeacherDTO> getTeachersByGroupAndCourse(String group, Course course);

    void saveTeachers(List<TeacherDTO> teacherDTOS);

    void updateTeachers(List<TeacherDTO> teacherDTOS);

    void deleteTeacherById(Long id);
}
