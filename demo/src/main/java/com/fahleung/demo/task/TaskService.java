package com.fahleung.demo.task;

import java.sql.Timestamp;
import java.util.Optional;

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

    public ResponseEntity<String> saveTask(Long user_id, String taskname, String tasklistname, String username) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(tasklistname, user_id);
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(username)) {
                return new ResponseEntity<>("Problem occured", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Task newTask = new Task(taskname.substring(0, 1).toUpperCase() + taskname.substring(1),
                    new Timestamp(System.currentTimeMillis()), false);
            newTask.setTasklist(tasklist.get());
            taskRepository.save(newTask);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteTask(Long user_id, String taskname, String tasklistname, String username) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(tasklistname, user_id);
        Optional<Task> task = taskRepository.findByNameAndTasklistName(taskname, tasklistname);
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(username)) {
                return new ResponseEntity<>("Problem occured", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!task.isPresent()) {
                return new ResponseEntity<>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }
            taskRepository.deleteByNameAndTasklistId(taskname, tasklist.get().getTasklist_id());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateTask(Long user_id, String taskname, String tasklistname, String username,
            boolean isCompleted) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(tasklistname, user_id);
        Optional<Task> task = taskRepository.findByNameAndTasklistName(taskname, tasklistname);
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(username)) {
                return new ResponseEntity<>("Problem occured", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (!tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!task.isPresent()) {
                return new ResponseEntity<>("Task doesn't exist", HttpStatus.BAD_REQUEST);
            }
            taskRepository.switchCompleteTaskByNameAndTasklistId(taskname, tasklist.get().getTasklist_id(),
                    isCompleted);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

}
