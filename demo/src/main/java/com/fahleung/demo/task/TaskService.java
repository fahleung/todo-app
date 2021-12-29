package com.fahleung.demo.task;

import java.sql.Timestamp;
import java.util.Optional;

import com.fahleung.demo.tasklist.Tasklist;
import com.fahleung.demo.tasklist.TasklistRepository;
import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TasklistRepository tasklistRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.tasklistRepository = tasklistRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> saveTask(TaskDto taskDto) {
        Optional<User> user = userRepository.findById(taskDto.getUser_id());
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(taskDto.getTasklistname(),
                taskDto.getUser_id());
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(taskDto.getLogUsername())) {
                return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Task newTask = new Task(
                    taskDto.getTaskname().substring(0, 1).toUpperCase() + taskDto.getTaskname().substring(1),
                    new Timestamp(System.currentTimeMillis()), false);
            newTask.setTasklist(tasklist.get());
            taskRepository.save(newTask);
            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteTask(TaskDto taskDto) {
        Optional<User> user = userRepository.findById(taskDto.getUser_id());
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(taskDto.getTasklistname(),
                taskDto.getUser_id());
        Optional<Task> task = taskRepository.findByNameAndTasklistName(taskDto.getTaskname(),
                taskDto.getTasklistname());
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(taskDto.getLogUsername())) {
                return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!task.isPresent()) {
                return new ResponseEntity<>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }
            taskRepository.deleteByNameAndTasklistId(taskDto.getTaskname(), tasklist.get().getTasklist_id());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateTask(TaskDto taskDto) {
        Optional<User> user = userRepository.findById(taskDto.getUser_id());
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(taskDto.getTasklistname(),
                taskDto.getUser_id());
        Optional<Task> task = taskRepository.findByNameAndTasklistName(taskDto.getTaskname(),
                taskDto.getTasklistname());
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(taskDto.getLogUsername())) {
                return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!task.isPresent()) {
                return new ResponseEntity<>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }
            taskRepository.switchCompleteTaskByNameAndTasklistId(taskDto.getTaskname(), tasklist.get().getTasklist_id(),
                    taskDto.isCompleted());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

}
