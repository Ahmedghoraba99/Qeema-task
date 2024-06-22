package com.example.API_Starter.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String name) {
        if (name == null) {
            return "Hello, World!";
        }
        return "Hello, " + name + "!";
    }

}
