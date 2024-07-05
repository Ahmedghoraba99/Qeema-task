package com.example.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api.entity.UserPrincipal;
import com.example.api.entity.Users;
import com.example.api.repository.UserRepository;
import com.example.api.rest.controlleradvice.UsersResponses.exceptions.UserNotFoundException;
import com.example.api.rest.controlleradvice.auth.requests.RegisterationRequest;
import com.example.api.rest.controlleradvice.auth.response.RegisterationResponse;

@Service
public class UsersServiceImpl implements UserDetailsService {

    private UsersService usersService;
    private UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(@Lazy UsersService usersService, UserRepository userRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    private Users registerationRequestToUser(RegisterationRequest registerationRequest) {
        return new Users(
                registerationRequest.getName(),
                registerationRequest.getEmail(),
                registerationRequest.getPassword()
        );
    }

    public RegisterationResponse registerUser(RegisterationRequest registerationRequest) {
        Users user = registerationRequestToUser(registerationRequest);
        userRepository.save(user);
        return new RegisterationResponse(user, "User registered successfully");
    }

    public Optional<Users> findByEmail(String email) {
        Optional<Users> temp = this.usersService.findByEmail(email);
        if (temp == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return temp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersService.findByEmail(username).get();

        if (user == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

}
