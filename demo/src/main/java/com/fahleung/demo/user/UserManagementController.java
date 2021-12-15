package com.fahleung.demo.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/users")
public class UserManagementController {

    private static final List<User> users = Arrays.asList(
            new User("hugo", "azerty123", "azerty123", "hugo@gmail.com"));

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user) {
        System.out.println("create user manager");
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        System.out.println("delete user manager");
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        System.out.println("update user manager");
    }
}
