package com.example.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Users;
import com.example.api.service.UsersService;

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
        return usersService.findAll();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable long id) {
        return usersService.findById(id).get();
    }

    @PostMapping("")
    public String postMethodName(@RequestBody Users user) {
        this.usersService.save(user);
        return "Saved";
    }

}
