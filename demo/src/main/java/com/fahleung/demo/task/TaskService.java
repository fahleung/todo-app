package com.fahleung.demo.task;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TasklistRepository tasklistRepository) {
        this.taskRepository = taskRepository;
        this.tasklistRepository = tasklistRepository;

    }

    public ResponseEntity<String> saveTask(Long tasklist_id, Task task) {
        Optional<Tasklist> tasklist = tasklistRepository.findById(tasklist_id);
        if (tasklist.isPresent()) {
            task.setTasklist(tasklist.get());
            taskRepository.save(task);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteTask(Long tasklist_id, Long task_id) {
        Optional<Task> task = taskRepository.findById(task_id);
        if (task.isPresent()) {
            taskRepository.deleteById(task_id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.NOT_FOUND);
    }

    public void updateTask(Long tasklist_id, Long task_id, String name) {
    }

}
