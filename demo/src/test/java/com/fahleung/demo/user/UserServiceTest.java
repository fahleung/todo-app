package com.fahleung.demo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder, userDao);
    }

    @Test
    void testGetUsers() {
        underTest.getUsers();
        verify(userRepository).findAll();
    }

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        String username = "Thomas";
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        userRepository.save(user);
        assertThat(userDao.selectUserByUsername(username).isPresent());
    }

    @Test
    void willThrowIfUsernameDoesntExist() {
        String username = "Thomas";
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        userRepository.save(user);
        assertThatThrownBy(() -> underTest.loadUserByUsername(username)).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining((String.format("Username %s not found", username)));

    }

    @Test
    void testRegisterShouldReturnAnInstanceOfBindingResult() {
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        assertThat(underTest.register(user, (BindingResult) errors)).isInstanceOf(BindingResult.class);
    }

    @Test
    void testRegisterIfEmailExists() {
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertTrue(underTest.register(user, (BindingResult) errors).getFieldErrorCount("email") == 1);
    }

    @Test
    void testRegisterIfUsernameExists() {
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertTrue(underTest.register(user, (BindingResult) errors).getFieldErrorCount("username") == 1);
    }

    @Test
    void testRegisterIfWrongPasswords() {
        User user = new User("Thomas", "azerty123", "ytreza123",
                "thomas@gmail.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        assertTrue(underTest.register(user, (BindingResult) errors).getFieldErrorCount("password") == 1);
    }

    @Test
    void testRegisterShouldSaveUser() {
        // given
        User user = new User("Thomas", "azerty123", "azerty123",
                "thomas@gmail.com");

        // when
        Errors errors = new BeanPropertyBindingResult(user, "user");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        underTest.register(user, (BindingResult) errors);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getUsername(), user.getUsername());
    }
}
