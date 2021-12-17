package com.fahleung.demo.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        } else {
            userService.register(user);
            return "redirect:/login?success=true";
        }

    }
}
