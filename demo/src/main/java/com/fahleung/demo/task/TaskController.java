package com.fahleung.demo.task;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "{user_id}")
    public ResponseEntity<String> saveTask(@PathVariable Long user_id, @RequestBody Map<String, String> body,
            Principal principal) {
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(user_id);
        taskDto.setTaskname(body.get("taskname"));
        taskDto.setTasklistname(body.get("tasklistname"));
        taskDto.setLogUsername(principal.getName());
        return taskService.saveTask(taskDto);
    }

    @DeleteMapping(path = "{user_id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long user_id, @RequestBody Map<String, String> body,
            Principal principal) {
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(user_id);
        taskDto.setTaskname(body.get("taskname"));
        taskDto.setTasklistname(body.get("tasklistname"));
        taskDto.setLogUsername(principal.getName());
        return taskService.deleteTask(taskDto);
    }

    @PutMapping(path = "{user_id}")
    public ResponseEntity<String> updateTask(@PathVariable Long user_id, @RequestBody Map<String, String> body,
            Principal principal) {
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(user_id);
        taskDto.setTaskname(body.get("taskname"));
        taskDto.setTasklistname(body.get("tasklistname"));
        taskDto.setLogUsername(principal.getName());
        taskDto.setCompleted(Boolean.parseBoolean(body.get("isCompleted")));
        return taskService.updateTask(taskDto);
    }

}
