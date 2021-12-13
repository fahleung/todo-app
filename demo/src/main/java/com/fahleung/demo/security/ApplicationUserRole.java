package com.fahleung.demo.security;

import java.util.Set;

import com.google.common.collect.Sets;
import static com.fahleung.demo.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(Sets.newHashSet(TASK_READ, TASK_WRITE)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, TASK_READ, TASK_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

}
