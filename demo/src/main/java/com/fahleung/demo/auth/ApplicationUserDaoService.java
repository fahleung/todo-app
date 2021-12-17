package com.fahleung.demo.auth;

import java.util.Optional;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserDao;
import com.fahleung.demo.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("ApplicationUserDaoService")
public class ApplicationUserDaoService implements UserDao {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> selectUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findUserByName(name);
    }
}