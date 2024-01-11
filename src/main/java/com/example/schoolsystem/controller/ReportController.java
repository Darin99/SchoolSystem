package com.example.schoolsystem.controller;

import com.example.schoolsystem.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ReportApi;
import org.openapitools.model.BaseDTO;
import org.openapitools.model.Course;
import org.openapitools.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ReportController implements ReportApi {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @Override
    public ResponseEntity<String> getStudentsCount() {
        log.info("getting students count ...");
        return ResponseEntity.ok(reportService.getStudentsCount());
    }

    @Override
    public ResponseEntity<String> getTeachersCount() {
        log.info("getting teachers count ...");
        return ResponseEntity.ok(reportService.getTeachersCount());
    }

    @Override
    public ResponseEntity<String> getCoursesByTypeCount(Course courseDto) {
        log.info("getting courses by type  count ...");
        return ResponseEntity.ok(reportService.getCoursesByTypeCount(courseDto));
    }

    @Override
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(Course courseDto) {
        log.info("getting students by type of course: {}", courseDto.getValue());
        return ResponseEntity.ok(reportService.getStudentsByCourse(courseDto));
    }

    @Override
    public ResponseEntity<List<StudentDTO>> getStudentsByGroup(String group) {
        log.info("getting students by group: {}", group);
        return ResponseEntity.ok(reportService.getStudentsByGroup(group));
    }

    @Override
    public ResponseEntity<List<BaseDTO>> getStudentsAndTeachersByGroupAndCourse(String group, Course courseDto) {
        log.info("getting students and teachers by group: {} and type of course {}", group, courseDto.getValue());
        return ResponseEntity.ok(reportService.getStudentsAndTeachersByGroupAndCourse(group, courseDto));
    }

    @Override
    public ResponseEntity<List<StudentDTO>> getStudentsOlderThanAndCourse(Integer age, Course courseDto) {
        log.info("getting students by type of course: {} and older than {}", courseDto.getValue(), age);
        return ResponseEntity.ok(reportService.getStudentsOlderThanAndCourse(age, courseDto));
    }
}
