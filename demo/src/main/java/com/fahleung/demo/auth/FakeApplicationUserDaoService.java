package com.fahleung.demo.auth;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.fahleung.demo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String name) {
        return getApplicationUsers().stream().filter(applicationUser -> name.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(USER.getGrantedAuthorities(), passwordEncoder.encode("test"), "test", true, true,
                        true, true),
                new ApplicationUser(ADMIN.getGrantedAuthorities(), passwordEncoder.encode("hugo"), "test", true, true,
                        true, true),
                new ApplicationUser(USER.getGrantedAuthorities(), passwordEncoder.encode("bob"), "test", true, true,
                        true, true));
        return applicationUsers;
    }

}
