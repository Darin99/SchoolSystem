package com.example.schoolsystem.repository;

import com.example.schoolsystem.model.commons.Course;
import com.example.schoolsystem.model.entity.BaseEntity;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public interface BaseEntityDAO<T extends BaseEntity> {

    default List<T> getCountOfEntities(Session session) {
        String query = "FROM " + getEntityClass().getSimpleName();
        TypedQuery<T> typedQuery = session.createQuery(query, getEntityClass());
        return typedQuery.getResultList();
    }

    default List<T> findEntitiesByCourse(Course course, Session session) {
        String query = "FROM " + getEntityClass().getSimpleName() + " WHERE course = :course";
        TypedQuery<T> typedQuery = session.createQuery(query, getEntityClass());
        typedQuery.setParameter("course", course);
        return typedQuery.getResultList();
    }

    default List<T> findEntitiesByGroup(String group, Session session) {
        String query = "FROM " + getEntityClass().getSimpleName() + " WHERE group_column = :group";
        TypedQuery<T> typedQuery = session.createQuery(query, getEntityClass());
        typedQuery.setParameter("group", group);
        return typedQuery.getResultList();
    }

    default List<T> findEntitiesByGroupAndCourse(String group, Course course, Session session) {
        String query = "FROM " + getEntityClass().getSimpleName() + " WHERE group_column = :group AND course = :course";
        TypedQuery<T> typedQuery = session.createQuery(query, getEntityClass());
        typedQuery.setParameter("group", group);
        typedQuery.setParameter("course", course);
        return typedQuery.getResultList();
    }

    default List<T> findEntitiesOlderThanAndByCourse(int age, Course course, Session session) {
        String query = "FROM " + getEntityClass().getSimpleName() +
                " WHERE age > :age AND course = :course";
        TypedQuery<T> typedQuery = session.createQuery(query, getEntityClass());
        typedQuery.setParameter("age", age);
        typedQuery.setParameter("course", course);
        return typedQuery.getResultList();
    }

    Class<T> getEntityClass();
}
