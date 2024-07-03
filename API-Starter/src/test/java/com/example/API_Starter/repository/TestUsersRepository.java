package com.example.API_Starter.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.API_Starter.entity.Users;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TestUsersRepository {

    @Autowired
    @Qualifier("userRepository") // Specify the bean name here
    private UserRepository usersRepository;

    @Test
    public void testUserSave() {
        // arr
        Users user = new Users("John Doe", "john@doe.com", "1234567890");
        // act
        usersRepository.save(user);
        // assert
        assert (usersRepository.findAll().size() == 1);
        System.out.println(usersRepository.findAll().size());
        System.out.println(user);
        assert (usersRepository.findById((long) user.getId()).get().getEmail().equals("john@doe.com"));
        // clean
        usersRepository.deleteAll();

    }

    @Test
    public void testUserUpdate() {
        // arr
        Users user = new Users("John Doe", "john@doe.com", "1234567890");
        usersRepository.save(user);
        // act
        user.setEmail("john@gmail.com");
        usersRepository.save(user);
        // assert
        assert (usersRepository.findAll().size() == 1);
        assert (usersRepository.findById((long) user.getId()).get().getEmail().equals("john@gmail.com"));
        // clean
        usersRepository.deleteAll();
    }

    @Test
    public void testUserDelete() {
        // arr
        Users user = new Users("John Doe", "john@doe.com", "1234567890");
        usersRepository.save(user);
        // act
        usersRepository.delete(user);
        // assert
        assert (usersRepository.findAll().isEmpty());
    }

    @Test
    public void testUserFindAll() {
        // arr
        Users user1 = new Users("John Doe", "john@doe.com", "1234567890");
        Users user2 = new Users("John Doe", "john2@doe.com", "1234567890");
        // act
        usersRepository.save(user1);
        usersRepository.save(user2);
        // assert
        assert (usersRepository.findAll().size() == 2);
        // clean
        usersRepository.deleteAll();
    }

    // test email uniquness
    @Test
    public void testUserEmailUniqueness() {
        // arr
        Users user1 = new Users("John Doe", "john@doe.com", "1234567890");
        Users user2 = new Users("John Doe", "john@doe.com", "1234567890");
        try {
            // act
            usersRepository.save(user1);
            usersRepository.save(user2);
            usersRepository.deleteAll();
            fail("Email should be unique");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // test password length
    @Test
    public void testUserPasswordLength() {
        // arr
        Users user1 = new Users("John Doe", "john@doe.com", "123");
        try {
            // act
            usersRepository.save(user1);
            usersRepository.deleteAll();
            fail("Password should be at least 8 characters");
        } catch (Exception e) {
            assertNotNull(e);
        }

        // clean
    }
}
