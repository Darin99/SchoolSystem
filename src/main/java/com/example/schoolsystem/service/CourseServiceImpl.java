package com.example.schoolsystem.service;


import com.example.schoolsystem.model.commons.Course;
import com.example.schoolsystem.model.entity.Student;
import com.example.schoolsystem.model.entity.Teacher;
import com.example.schoolsystem.repository.BaseEntityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final BaseEntityDAO<Student> studentRepository;
    private final BaseEntityDAO<Teacher> teacherRepository;
    private final SessionFactory sessionFactory;

    public CourseServiceImpl(BaseEntityDAO<Student> studentRepository, BaseEntityDAO<Teacher> teacherRepository, SessionFactory sessionFactory) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getCoursesByTypeCount(org.openapitools.model.Course courseDto) {
        int studentsSizeByCourse = studentRepository.findEntitiesByCourse(Course.getCourse(courseDto.name()),
                getSession()).size();
        int teachersSizeByCourse = teacherRepository.findEntitiesByCourse(Course.getCourse(courseDto.name()),
                getSession()).size();
        return String.format("Currently there are %s courses with type: %s saved in the db.",
                studentsSizeByCourse + teachersSizeByCourse, courseDto.getValue());
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
