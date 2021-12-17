package com.fahleung.demo.user;

import java.util.Optional;

public interface UserDao {
    public Optional<User> selectUserByUsername(String name);
}
