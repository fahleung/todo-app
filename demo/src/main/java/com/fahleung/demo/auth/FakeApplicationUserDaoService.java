package com.fahleung.demo.auth;

import java.util.List;
import java.util.Optional;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserDao;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.fahleung.demo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectUserByUsername(String name) {
        /*
         * return getApplicationUsers().stream().filter(applicationUser ->
         * name.equals(applicationUser.getUsername()))
         * .findFirst();
         */
        return null;
    }

    private List<User> getApplicationUsers() {
        return null;
        /*
         * Lists.newArrayList(
         * new User(USER.getGrantedAuthorities(), passwordEncoder.encode("test"),
         * "test", true, true,
         * true, true),
         * new User(ADMIN.getGrantedAuthorities(), passwordEncoder.encode("hugo"),
         * "test", true, true,
         * true, true),
         * new User(USER.getGrantedAuthorities(), passwordEncoder.encode("bob"), "test",
         * true, true,
         * true, true));
         */

    }

}
