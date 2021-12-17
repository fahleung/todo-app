package com.fahleung.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            @Qualifier("ApplicationUserDaoService") UserDao userDao) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User register(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        } else {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                User userRegister = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
                        user.getEmail());
                userRepository.save(userRegister);
                return userRegister;
            } else {
                throw new IllegalStateException("password do not match");
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.selectUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
    }
}
