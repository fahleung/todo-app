package com.fahleung.demo.task;

public class TaskDto {
    private Long user_id;
    private String taskname;
    private String tasklistname;
    private String logUsername;
    private boolean isCompleted;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTasklistname() {
        return tasklistname;
    }

    public void setTasklistname(String tasklistname) {
        this.tasklistname = tasklistname;
    }

    public String getLogUsername() {
        return logUsername;
    }

    public void setLogUsername(String logUsername) {
        this.logUsername = logUsername;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
