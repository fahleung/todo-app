package com.fahleung.demo.auth;

public interface ApplicationUserDao {
    public ApplicationUser selectApplicationUserByUsername(String name);
}
