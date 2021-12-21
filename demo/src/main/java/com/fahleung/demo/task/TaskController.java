package com.fahleung.demo.task;

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

    @PostMapping(path = { "tasklist_id" })
    public ResponseEntity<String> saveTask(@PathVariable Long tasklist_id, @RequestBody Task task) {
        return taskService.saveTask(tasklist_id, task);
    }

    @DeleteMapping(path = "{tasklist_id}/{task_id}")
    public ResponseEntity<String> deleteTask(@PathVariable("tasklist_id") Long tasklist_id,
            @PathVariable("task_id") Long task_id) {
        return taskService.deleteTask(tasklist_id, task_id);
    }

    @PutMapping(path = "{tasklist_id}/{task_id}")
    public void updateTask(@PathVariable("tasklist_id") Long tasklist_id, @PathVariable("task_id") Long task_id,
            @RequestParam String name) {
        taskService.updateTask(tasklist_id, task_id, name);
    }

}
