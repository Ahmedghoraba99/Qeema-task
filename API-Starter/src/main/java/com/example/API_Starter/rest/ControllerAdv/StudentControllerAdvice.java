package com.example.API_Starter.rest.ControllerAdv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.API_Starter.rest.StudentResponses.Exceptions.StudentNotFoundException;
import com.example.API_Starter.rest.StudentResponses.ResponseError;
// Interceptor for exceptions

@ControllerAdvice
public class StudentControllerAdvice {

    @ExceptionHandler
    public ResponseEntity handleStudentNotFoundException(StudentNotFoundException ex) {
        ResponseError res = new ResponseError("Student not found", 404, System.currentTimeMillis());
        return ResponseEntity.status(404).body(res);
    }

    // General middleware handler
    @ExceptionHandler
    public ResponseEntity handleGeneralException(Exception ex) {
        ResponseError res = new ResponseError("An error occurred", 500, System.currentTimeMillis());
        return ResponseEntity.status(500).body(res);
    }
}
