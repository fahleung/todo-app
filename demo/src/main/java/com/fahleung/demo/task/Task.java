package com.fahleung.demo.task;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private Long task_id;
    @ManyToOne(targetEntity = Tasklist.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tasklist_id", insertable = false, updatable = false)
    private Tasklist tasklist;
    @Column(name = "tasklist_id")
    private Long tasklist_id;
    @NotBlank
    private String name;
    @NotNull
    private Timestamp time;
    private boolean completed = false;

    public Task() {
    }

    public Task(String name, Timestamp time, boolean completed) {
        this.name = name;
        this.time = time;
        this.completed = completed;
    }

    public Task(Long tasklist_id, String name, Timestamp time, boolean completed) {
        this.tasklist_id = tasklist_id;
        this.name = name;
        this.time = time;
        this.completed = completed;
    }

    public Tasklist getTasklist() {
        return tasklist;
    }

    public void setTasklist(Tasklist tasklist) {
        this.tasklist = tasklist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
