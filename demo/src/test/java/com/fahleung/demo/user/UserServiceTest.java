package com.fahleung.demo.user;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // when
        underTest.getUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    @Disabled
    void testLoadUserByUsername() throws UsernameNotFoundException {
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";

        // given
        User user = new User(username, password, password,
                email);
        userRepository.save(user);

        assertInstanceOf(UserDetails.class, underTest.loadUserByUsername(username));
    }

    @Test
    void testRegister() {
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";

        // given
        User user = new User(username, password, password,
                email);
        userRepository.save(user);

    }
}
