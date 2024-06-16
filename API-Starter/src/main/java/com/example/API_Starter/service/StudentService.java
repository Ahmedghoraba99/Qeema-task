package com.example.API_Starter.service;

import com.example.API_Starter.entity.Student;

public interface StudentService {

    Iterable<Student> findAll();

    Student findById(int id);
    // Student save(Student student);
    // void deleteById(Long id);
    // void deleteAll();
}
