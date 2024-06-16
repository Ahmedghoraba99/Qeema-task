package com.example.API_Starter.dao;

import com.example.API_Starter.entity.Student;
// Entity

public interface StudentDao {

    void save(Student student);

    public Student updateById(int id, Student student);

    Student findById(int id);

    Student deleteById(int id);

    // Get all 
    Iterable<Student> findAll();

}
