package com.example.schoolsystem.controller;

import com.example.schoolsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.StudentsApi;
import org.openapitools.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class StudentController implements StudentsApi {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> saveStudents(List<StudentDTO> studentDTOS) {
        log.info("saving students...");
        service.saveStudents(studentDTOS);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateStudents(List<StudentDTO> studentDTOS) {
        log.info("updating students...");
        service.updateStudents(studentDTOS);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteStudentById(Long id) {
        log.info("deleting student by id: {}", id);
        service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
