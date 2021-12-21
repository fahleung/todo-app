package com.fahleung.demo.task;

import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Table(name = "tasklists")
public class Tasklist {
    @Id
    @SequenceGenerator(name = "tasklist_sequence", sequenceName = "tasklist_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasklist_sequence")
    private Long tasklist_id;
    @OneToMany(targetEntity = Task.class, mappedBy = "task_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private Set<Task> tasks;
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "user_id")
    private Long user_id;
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

    public Tasklist(Long user_id, String name) {
        this.user_id = user_id;
        this.name = name;
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