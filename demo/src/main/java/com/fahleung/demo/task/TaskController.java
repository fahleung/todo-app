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
import org.springframework.web.bind.annotation.RequestParam;
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
        return taskService.saveTask(user_id, body.get("taskname"), body.get("tasklistname"), principal.getName());
    }

    @DeleteMapping(path = "{user_id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long user_id, @RequestBody Map<String, String> body,
            Principal principal) {
        return taskService.deleteTask(user_id, body.get("taskname"), body.get("tasklistname"), principal.getName());
    }

    @PutMapping(path = "{tasklist_id}/{task_id}")
    public void updateTask(@PathVariable("tasklist_id") Long tasklist_id, @PathVariable("task_id") Long task_id,
            @RequestParam String name) {
        taskService.updateTask(tasklist_id, task_id, name);
    }

}
