package com.example.API_Starter.rest.StudentResponses.Exceptions;

public class StudentNotFoundException extends RuntimeException {

    // Constructors of the parent class
    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }

}
