package com.example.schoolsystem.service;

import com.example.schoolsystem.exception.ResourceNotFoundException;
import org.openapitools.model.BaseDTO;
import org.openapitools.model.Course;
import org.openapitools.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public ReportServiceImpl(StudentService studentService, TeacherService teacherService, CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @Override
    public String getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @Override
    public String getTeachersCount() {
        return teacherService.getTeachersCount();
    }

    @Override
    public String getCoursesByTypeCount(Course courseDto) {
        return courseService.getCoursesByTypeCount(courseDto);
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(Course courseDto) {
        return studentService.getStudentsByCourse(courseDto);
    }

    @Override
    public List<StudentDTO> getStudentsByGroup(String group) {
        return studentService.getStudentsByGroup(group);
    }

    @Override
    public List<BaseDTO> getStudentsAndTeachersByGroupAndCourse(String group, Course courseDto) {
        List<BaseDTO> students = studentService.getStudentsByGroupAndCourse(group, courseDto)
                .stream()
                .map(studentDTO -> mapToBaseDto(studentDTO.getName(),
                        studentDTO.getAge(),
                        studentDTO.getGroup(),
                        BaseDTO.CourseEnum.fromValue(studentDTO.getCourse().getValue())))
                .toList();

        List<BaseDTO> teachers = teacherService.getTeachersByGroupAndCourse(group, courseDto)
                .stream()
                .map(teacherDTO -> mapToBaseDto(teacherDTO.getName(),
                        teacherDTO.getAge(),
                        teacherDTO.getGroup(),
                        BaseDTO.CourseEnum.fromValue(teacherDTO.getCourse().getValue())))
                .toList();
        List<BaseDTO> baseDTOS = new ArrayList<>();
        baseDTOS.addAll(students);
        baseDTOS.addAll(teachers);
        if (baseDTOS.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("No students and teachers found for group: %s and type of course: %s",
                            group, courseDto.getValue()));
        }
        return baseDTOS;
    }

    @Override
    public List<StudentDTO> getStudentsOlderThanAndCourse(Integer age, Course courseDto) {
        return studentService.getStudentsOlderThanAndCourse(age, courseDto);
    }

    private BaseDTO mapToBaseDto(String name, int age, String group, BaseDTO.CourseEnum course) {
        return new BaseDTO(name, age, group, course);
    }
}
