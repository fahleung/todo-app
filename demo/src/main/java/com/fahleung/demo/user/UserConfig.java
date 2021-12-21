package com.fahleung.demo.user;

import java.sql.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fahleung.demo.task.Task;
import com.fahleung.demo.task.TaskRepository;
import com.fahleung.demo.task.Tasklist;
import com.fahleung.demo.task.TasklistRepository;

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
            User fabien = new User("fabien", passwordEncoder.encode("azerty123"), passwordEncoder.encode("azerty123"),
                    "fabien@gmail.com");
            // CREATE TASKLISTS
            Tasklist tasklist1 = new Tasklist((long) 1, "tasklist1");
            Tasklist tasklist2 = new Tasklist((long) 1, "tasklist2");
            Tasklist tasklist3 = new Tasklist((long) 1, "tasklist3");
            Set<Tasklist> set = new HashSet<Tasklist>();
            // CREATE TASKS
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Task task1 = new Task((long) 1, "task1", stamp, false);
            Task task2 = new Task((long) 1, "task2", stamp, false);
            Task task3 = new Task((long) 1, "task3", stamp, false);
            Task task4 = new Task((long) 1, "task4", stamp, false);
            Task task5 = new Task((long) 1, "task5", stamp, false);
            Task task6 = new Task((long) 1, "task6", stamp, false);
            Task task7 = new Task((long) 1, "task7", stamp, false);
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
