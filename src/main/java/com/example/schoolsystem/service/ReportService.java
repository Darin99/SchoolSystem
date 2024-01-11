package com.example.schoolsystem.service;

import org.openapitools.model.BaseDTO;
import org.openapitools.model.Course;
import org.openapitools.model.StudentDTO;

import java.util.List;

public interface ReportService {


    String getStudentsCount();

    String getTeachersCount();

    String getCoursesByTypeCount(Course courseDto);

    List<StudentDTO> getStudentsByCourse(Course courseDto);

    List<StudentDTO> getStudentsByGroup(String group);

    List<BaseDTO> getStudentsAndTeachersByGroupAndCourse(String group, Course courseDto);

    List<StudentDTO> getStudentsOlderThanAndCourse(Integer age, Course courseDto);
}
