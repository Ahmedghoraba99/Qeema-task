package com.example.API_Starter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    public Coach myCoach;

    @Autowired
    public ApiController(Coach mCoach) {
        super();
        this.myCoach = mCoach;
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String name) {
        if (name == null) {
            return "Hello, World!";
        }
        return "Hello, " + name + "!";
    }

    @GetMapping("/coach")
    public String getWorkout() {
        return myCoach.getDailyWorkout();
    }

}
