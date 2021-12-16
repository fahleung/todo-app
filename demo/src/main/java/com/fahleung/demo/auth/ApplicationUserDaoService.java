package com.fahleung.demo.auth;

import java.util.Optional;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ApplicationUserDaoService")
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApplicationUser selectApplicationUserByUsername(String name) {
        Optional<User> user = userRepository.findUserByName(name);

        if (user.isPresent()) {
            return new ApplicationUser(user.get().getRole().getGrantedAuthorities(),
                    user.get().getPassword(),
                    user.get().getName(), user.get().isAccountNonExpired(), user.get().isAccountNonLocked(),
                    user.get().isCredentialsNonExpired(), user.get().isEnabled());
        } else {
            return null;
        }
    }
}