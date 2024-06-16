package com.example.API_Starter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.API_Starter.dao.UsersDao;
import com.example.API_Starter.entity.UserPrincipal;
import com.example.API_Starter.entity.Users;
import com.example.API_Starter.rest.UsersResponses.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    @Autowired
    private UsersDao usersDao;

    public UsersServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public UsersServiceImpl() {
    }

    @Override
    public Iterable<Users> getAll() {
        Iterable<Users> users = usersDao.findAll();
        if (users == null) {
            throw new UserNotFoundException("No users found");
        }
        return users;
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        Users temp = usersDao.findByEmail(email);
        if (temp == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return Optional.of(temp);
    }

    @Override
    public Users getById(int id) {
        Users user = usersDao.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    @Override
    @Transactional
    public Users add(Users user) {
        return usersDao.save(user);
    }

    @Override
    @Transactional
    public Users update(Users user) {
        return usersDao.save(user);
    }

    @Override
    public void delete(int id) {
        usersDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersDao.findByEmail(username);

        if (user == null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }

}
