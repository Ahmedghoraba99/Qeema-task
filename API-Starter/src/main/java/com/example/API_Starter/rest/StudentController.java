package com.example.API_Starter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Starter.entity.Student;
import com.example.API_Starter.rest.StudentResponses.Exceptions.StudentNotFoundException;
import com.example.API_Starter.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    StudentService studentService;

    // Get all students
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public Iterable<Student> getAllStudents() {
        Iterable<Student> students = studentService.findAll();
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        Student student = studentService.findById(id);
        // Bad Scenario
        if (student == null) {
            throw new StudentNotFoundException("Student with id: " + id + " not found");
        }
        return student;
    }

    @PostMapping("")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request

        return entity;
    }

}

// Create new student
// Update student
// Delete student
// Search students by name

