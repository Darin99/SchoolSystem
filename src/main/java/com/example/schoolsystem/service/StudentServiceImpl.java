package com.example.schoolsystem.service;

import com.example.schoolsystem.exception.ResourceNotFoundException;
import com.example.schoolsystem.model.commons.Course;
import com.example.schoolsystem.model.entity.Student;
import com.example.schoolsystem.repository.BaseEntityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openapitools.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final BaseEntityDAO<Student> repository;
    private final SessionFactory sessionFactory;

    @Autowired
    public StudentServiceImpl(BaseEntityDAO<Student> repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getStudentsCount() {
        List<Student> students = repository.getCountOfEntities(getSession());
        return String.format("Currently there are %s students saved in the db.", students.size());
    }

    @Override
    public List<StudentDTO> getStudentsByGroup(String group) {
        String errorMessage = String.format("No students found for group: %s", group);
        List<Student> students = repository.findEntitiesByGroup(group, getSession());
        checkIfResultIsEmpty(students, errorMessage);

        return students.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(org.openapitools.model.Course courseDto) {
        String errorMessage = String.format("No students found for course: %s", courseDto.getValue());
        List<Student> students = repository.findEntitiesByCourse(getCourse(courseDto.getValue()), getSession());
        checkIfResultIsEmpty(students, errorMessage);

        return students.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsByGroupAndCourse(String group, org.openapitools.model.Course courseDto) {
        List<Student> students = repository.findEntitiesByGroupAndCourse(group, getCourse(courseDto.getValue()), getSession());

        return students.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsOlderThanAndCourse(Integer age, org.openapitools.model.Course courseDto) {
        String errorMessage = String.format("No students found for course: %s and older than %s", courseDto, age);
        List<Student> students = repository.findEntitiesOlderThanAndByCourse(age, getCourse(courseDto.getValue()), getSession());
        checkIfResultIsEmpty(students, errorMessage);

        return students.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveStudents(List<StudentDTO> studentDTOS) {
        if (studentDTOS.isEmpty()) {
            return;
        }
        studentDTOS.stream().map(this::mapToEntity).forEach(student -> getSession().save(student));
    }

    @Override
    public void updateStudents(List<StudentDTO> studentDTOS) {
        if (studentDTOS.isEmpty()) {
            return;
        }
        studentDTOS.forEach(studentDTO -> {
            Session session = getSession();
            Student student = session.get(Student.class, studentDTO.getId());
            if (student != null) {
                session.evict(student);
                updateEntity(student, studentDTO);
                session.update(student);
            } else {
                throw new ResourceNotFoundException(String.format("No student found for given id: %s",
                        studentDTO.getId()));
            }
        });
    }

    @Override
    public void deleteStudentById(Long id) {
        Session session = getSession();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.delete(student);
        } else {
            throw new ResourceNotFoundException(String.format("No students found for given id: %s", id));
        }
    }

    private void checkIfResultIsEmpty(List<Student> students, String errorMessage) {
        if (students.size() == 0) {
            throw new ResourceNotFoundException(errorMessage);
        }
    }

    private Course getCourse(String courseName) {
        return Course.getCourse(courseName);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private Student mapToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGroup(dto.getGroup());
        student.setCourse(getCourse(dto.getCourse().name()));
        return student;
    }

    private void updateEntity(Student studentToUpdate, StudentDTO updatedStudent) {
        studentToUpdate.setName(updatedStudent.getName());
        studentToUpdate.setAge(updatedStudent.getAge());
        studentToUpdate.setGroup(updatedStudent.getGroup());
        studentToUpdate.setCourse(getCourse(updatedStudent.getCourse().getValue()));
    }

    private StudentDTO mapToDto(Student student) {
        StudentDTO.CourseEnum course = StudentDTO.CourseEnum.valueOf(student.getCourse().name());
        return new StudentDTO(student.getName(), student.getAge(), student.getGroup(), course);
    }
}
