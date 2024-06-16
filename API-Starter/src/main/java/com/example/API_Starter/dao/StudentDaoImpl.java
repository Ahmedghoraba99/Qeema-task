package com.example.API_Starter.dao;

import org.springframework.stereotype.Repository;

import com.example.API_Starter.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
// Entity functions implemented in this class

@Repository
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public Student deleteById(int id) {
        Student tmpStudent = findById(id);
        entityManager.remove(tmpStudent);
        return tmpStudent;
    }

    @Transactional
    @Override
    public Student updateById(int id, Student newStudent) {
        Student tmpStudent = findById(id);

        // update student
        tmpStudent.setFirstName(newStudent.getFirstName());
        tmpStudent.setLastName(newStudent.getLastName());

        tmpStudent = entityManager.merge(tmpStudent);
        return tmpStudent;
    }

    @Override
    public Iterable<Student> findAll() {
        // findAll students
        Query query = entityManager.createQuery("SELECT s FROM Students s", Student.class);
        Iterable<Student> students = query.getResultList();
        return students;
    }

}
