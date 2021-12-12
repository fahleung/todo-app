package com.fahleung.demo.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public List<User> getUsers() {
        return List.of(
                new User(1L, "fabien", "azerty123", "fabien@hmail.com"));
    }

}
