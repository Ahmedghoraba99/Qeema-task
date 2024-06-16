package com.example.API_Starter.dao;

import org.springframework.stereotype.Repository;

import com.example.API_Starter.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsersDaoImpl implements UsersDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Iterable<Users> findAll() {
        return entityManager.createQuery("SELECT u FROM Users u", Users.class).getResultList();
    }

    @Override
    public Users findById(int id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public Users save(Users entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Users entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(int id) {
        Users entity = findById(id);
        if (entity != null) {
            delete(entity);
        } else {
            throw new IllegalArgumentException("Entity not found");
        }
    }

    @Override
    public Users findByName(String name) {
        return entityManager.createQuery("SELECT u FROM Users u WHERE u.name = :name", Users.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public Users findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class)
                .setParameter("email", email).getSingleResult();
    }

}
