package com.fahleung.demo.auth;

import java.util.Optional;

public interface ApplicationUserDao {
    public Optional<ApplicationUser> selectApplicationUserByUsername(String name);
}
