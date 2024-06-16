package com.example.API_Starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Starter.dao.StudentDao;
import com.example.API_Starter.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    StudentServiceImpl(StudentDao studentDaoInj) {
        studentDao = studentDaoInj;
    }

    @Override
    public Iterable<Student> findAll() {
        Iterable<Student> res = studentDao.findAll();
        return res;
    }

    @Override
    public Student findById(int id) {
        Student res = studentDao.findById(id);
        return res;
    }
}
