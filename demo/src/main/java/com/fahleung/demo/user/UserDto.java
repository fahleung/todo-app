package com.fahleung.demo.user;

import java.util.List;

import com.fahleung.demo.tasklist.Tasklist;

public class UserDto {

    private Long user_id;
    private String username;
    private List<Tasklist> tasklists;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Tasklist> getTasklists() {
        return tasklists;
    }

    public void setTasklists(List<Tasklist> tasklists) {
        this.tasklists = tasklists;
    }

}
