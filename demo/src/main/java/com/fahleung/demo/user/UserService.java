package com.fahleung.demo.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        } else {
            userRepository.save(user);
            return user;
        }
    }

    public User logUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            throw new IllegalStateException("user not found");
        } else {
            System.out.println("good");
        }
        return null;
    }
}
