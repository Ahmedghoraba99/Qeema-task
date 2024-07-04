package com.example.API_Starter.rest.controlleradvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.API_Starter.rest.controlleradvice.UsersResponses.exceptions.UserNotFoundException;

@ControllerAdvice
public class UsersControllerAdvice {

    @ExceptionHandler
    public ResponseEntity handleUsertNotFoundException(UserNotFoundException ex) {
        ResponseError res = new ResponseError(
                "User not found",
                404, System.currentTimeMillis()
        );
        return ResponseEntity.status(404).body(res);
    }

    // General middleware handler
    @ExceptionHandler
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ResponseError res = new ResponseError(
                "An error occurred" + ex.getMessage(),
                500, System.currentTimeMillis()
        );
        return ResponseEntity.status(500).body(res);
    }

}
