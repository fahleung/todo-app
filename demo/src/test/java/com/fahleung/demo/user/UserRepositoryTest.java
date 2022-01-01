package com.fahleung.demo.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindByEmail() {
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";

        // given
        User user = new User(username, password, password,
                email);
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findUserByEmail(email);
        // then
        assertTrue(userOptional.isPresent());
    }

    @Test
    void itShouldFindUserByName() {
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";

        // given
        User user = new User(username, password, password,
                email);
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findUserByName(username);
        // then
        assertTrue(userOptional.isPresent());
    }
}
