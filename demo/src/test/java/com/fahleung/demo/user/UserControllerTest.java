package com.fahleung.demo.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    private UserController underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserController(userService);
    }

    @Test
    @Disabled
    void testGetUsers() { // To test later for admin purposes
        // assertTrue(userService.getUsers().equals(underTest.getUsers()));
    }

    @Test
    void testRegisterUser() {
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        when(userService.register(user, (BindingResult) errors)).thenReturn((BindingResult) errors);
        assertTrue(underTest.registerUser(user, (BindingResult) errors).equals("redirect:/login?success=true"));
    }

    @Test
    void testRegisterUserWithBindingErrors() {
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        errors.rejectValue("email", "email.user", "Binding Errors test");
        assertTrue(underTest.registerUser(user, (BindingResult) errors).equals("login"));
    }
}
