package com.example.API_Starter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Starter.entity.Users;
import com.example.API_Starter.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    public Iterable<Users> getUsers() {
        return usersService.getAll();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable int id) {
        return usersService.getById(id);
    }

    @PostMapping("")
    public String postMethodName(@RequestBody String entity) {
        return "Success";
    }

}
