package com.example.schoolsystem.service;

import com.example.schoolsystem.exception.ResourceNotFoundException;
import com.example.schoolsystem.model.entity.Teacher;
import com.example.schoolsystem.repository.BaseEntityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openapitools.model.Course;
import org.openapitools.model.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final BaseEntityDAO<Teacher> repository;
    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherServiceImpl(BaseEntityDAO<Teacher> repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getTeachersCount() {
        List<Teacher> teachers = repository.getCountOfEntities(getSession());
        return String.format("Currently there are %s teachers saved in the db.", teachers.size());
    }

    @Override
    public List<TeacherDTO> getTeachersByGroup(String group) {
        String errorMessage = String.format("No teachers found for group: %s", group);
        List<Teacher> teachers = repository.findEntitiesByGroup(group, getSession());
        checkIfResultIsEmpty(teachers, errorMessage);

        return teachers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTeachersByCourse(Course courseDto) {
        String errorMessage = String.format("No teachers found for course: %s", courseDto.getValue());
        List<Teacher> teachers = repository.findEntitiesByCourse(getCourse(courseDto.getValue()), getSession());
        checkIfResultIsEmpty(teachers, errorMessage);

        return teachers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTeachersByGroupAndCourse(String group, Course courseDto) {
        List<Teacher> teachers = repository.findEntitiesByGroupAndCourse(group, getCourse(courseDto.getValue()), getSession());

        return teachers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTeachers(List<TeacherDTO> teacherDTOS) {
        if (teacherDTOS.isEmpty()) {
            return;
        }
        teacherDTOS.stream().map(this::mapToEntity).forEach(teacher -> getSession().save(teacher));
    }

    @Override
    public void updateTeachers(List<TeacherDTO> teacherDTOS) {
        if (teacherDTOS.isEmpty()) {
            return;
        }
        teacherDTOS.forEach(teacherDTO -> {
            Session session = getSession();
            Teacher teacher = session.get(Teacher.class, teacherDTO.getId());
            if (teacher != null) {
                session.evict(teacher);
                updateEntity(teacher, teacherDTO);
                session.update(teacher);
            } else {
                throw new ResourceNotFoundException(String.format("No teacher found for given id: %s",
                        teacherDTO.getId()));
            }
        });
    }

    @Override
    public void deleteTeacherById(Long id) {
        Session session = getSession();
        Teacher teacher = session.get(Teacher.class, id);
        if (teacher != null) {
            session.delete(teacher);
        } else {
            throw new ResourceNotFoundException(String.format("No teachers found for given id: %s", id));
        }
    }

    private com.example.schoolsystem.model.commons.Course getCourse(String courseName) {
        return com.example.schoolsystem.model.commons.Course.getCourse(courseName);
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private void checkIfResultIsEmpty(List<Teacher> teachers, String errorMessage) {
        if (teachers.size() == 0) {
            throw new ResourceNotFoundException(errorMessage);
        }
    }

    private Teacher mapToEntity(TeacherDTO dto) {
        Teacher teacher = new Teacher();
        if (dto.getId() != null) {
            teacher.setId(dto.getId());
        }
        teacher.setName(dto.getName());
        teacher.setAge(dto.getAge());
        teacher.setGroup(dto.getGroup());
        teacher.setCourse(getCourse(dto.getCourse().name()));
        return teacher;
    }

    private void updateEntity(Teacher teacherToUpdate, TeacherDTO updatedTeacher) {
        teacherToUpdate.setName(updatedTeacher.getName());
        teacherToUpdate.setAge(updatedTeacher.getAge());
        teacherToUpdate.setGroup(updatedTeacher.getGroup());
        teacherToUpdate.setCourse(getCourse(updatedTeacher.getCourse().getValue()));
    }

    private TeacherDTO mapToDto(Teacher teacher) {
        TeacherDTO.CourseEnum course = TeacherDTO.CourseEnum.fromValue(teacher.getCourse().name());
        return new TeacherDTO(teacher.getName(), teacher.getAge(), teacher.getGroup(), course);
    }
}
