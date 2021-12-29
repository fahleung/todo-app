package com.fahleung.demo.user;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fahleung.demo.task.Task;
import com.fahleung.demo.task.TaskRepository;
import com.fahleung.demo.tasklist.Tasklist;
import com.fahleung.demo.tasklist.TasklistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository, TasklistRepository tasklistRepository,
            TaskRepository taskRepository) {
        return args -> {
            // CREATE USER
            User fabien = new User("Fabien", passwordEncoder.encode("azerty123"), passwordEncoder.encode("azerty123"),
                    "fabien@gmail.com");
            repository.save(fabien);
            // CREATE TASKLISTS
            Tasklist tasklist1 = new Tasklist("Tasklist1");
            Tasklist tasklist2 = new Tasklist("Tasklist2");
            Tasklist tasklist3 = new Tasklist("Tasklist3");
            tasklist1.setUser(fabien);
            tasklist2.setUser(fabien);
            tasklist3.setUser(fabien);
            Set<Tasklist> set = new HashSet<Tasklist>();
            // CREATE TASKS
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Task task1 = new Task("Task1", stamp, false);
            Task task2 = new Task("Task2", stamp, true);
            Task task3 = new Task("Task3", stamp, false);
            Task task4 = new Task("Task4", stamp, false);
            Task task5 = new Task("Task5", stamp, false);
            Task task6 = new Task("Task6", stamp, false);
            Task task7 = new Task("Task7", stamp, false);
            task1.setTasklist(tasklist1);
            task2.setTasklist(tasklist1);
            task3.setTasklist(tasklist2);
            task4.setTasklist(tasklist2);
            task5.setTasklist(tasklist2);
            task6.setTasklist(tasklist3);
            task7.setTasklist(tasklist3);

            Set<Task> set1 = new HashSet<Task>();
            Set<Task> set2 = new HashSet<Task>();
            Set<Task> set3 = new HashSet<Task>();
            set1.addAll(List.of(task1, task2));
            set2.addAll(List.of(task3, task4));
            set3.addAll(List.of(task5, task6, task7));

            tasklist1.setTasks(set1);
            tasklist2.setTasks(set2);
            tasklist3.setTasks(set3);

            set.add(tasklist1);
            set.add(tasklist2);
            set.add(tasklist3);

            fabien.setTasklists(set);

            repository.save(fabien);

        };
    }
}
