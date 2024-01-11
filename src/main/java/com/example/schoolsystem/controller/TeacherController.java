package com.example.schoolsystem.controller;

import com.example.schoolsystem.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.TeachersApi;
import org.openapitools.model.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TeacherController implements TeachersApi {

    private final TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> saveTeachers(List<TeacherDTO> teacherDTOS) {
        log.info("saving teachers...");
        service.saveTeachers(teacherDTOS);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateTeachers(List<TeacherDTO> teacherDTOS) {
        log.info("updating teachers...");
        service.updateTeachers(teacherDTOS);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTeacherById(Long id) {
        log.info("deleting teacher by id: {}", id);
        service.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }
}
