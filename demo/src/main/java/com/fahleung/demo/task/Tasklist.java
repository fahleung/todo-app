package com.fahleung.demo.task;

import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fahleung.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tasklists")
public class Tasklist {
    @Id
    @SequenceGenerator(name = "tasklist_sequence", sequenceName = "tasklist_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasklist_sequence")
    private Long tasklist_id;
    @OneToMany(targetEntity = Task.class, mappedBy = "tasklist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    @JsonManagedReference
    private Set<Task> tasks;
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @NotBlank
    private String name;

    public Tasklist() {

    }

    public Tasklist(Set<Task> tasks, String name) {
        this.tasks = tasks;
        this.name = name;
    }

    public Tasklist(String name) {
        this.name = name;
    }

    public Long getTasklist_id() {
        return tasklist_id;
    }

    public void setTasklist_id(Long tasklist_id) {
        this.tasklist_id = tasklist_id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}