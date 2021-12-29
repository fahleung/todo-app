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
import org.springframework.validation.BindingResult;

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

    public BindingResult register(User user, BindingResult bindingResult) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            if (userOptional.get().getEmail().equals(user.getEmail())) {
                bindingResult.rejectValue("email", "email.user", "Email already taken");
            }
            if (userOptional.get().getUsername().equals(user.getUsername())) {
                bindingResult.rejectValue("username", "username.user", "Username already taken");
            }
        }
        if (!(user.getPassword().equals(user.getConfirmPassword()))) {
            bindingResult.rejectValue("password", "password.user", "Passwords do not match");
        }
        if (!bindingResult.hasErrors()) {
            User userRegister = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
                    user.getEmail());
            userRepository.save(userRegister);
        }
        return bindingResult;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
