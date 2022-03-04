package com.fahleung.demo.tasklist;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TasklistService {

    private final TasklistRepository tasklistRepository;
    private final UserRepository userRepository;

    @Autowired
    public TasklistService(TasklistRepository tasklistRepository, UserRepository userRepository) {
        this.tasklistRepository = tasklistRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> saveTasklist(TasklistDto tasklistDto) {
        Optional<User> user = userRepository.findById(tasklistDto.getUser_id());
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(tasklistDto.getName(),
                tasklistDto.getUser_id());
        if (!user.isPresent()) {
            return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
        }
        // check with current logged user
        if (!user.get().getUsername().equals(tasklistDto.getLogUsername())) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }
        // check if tasklist exist
        if (tasklist.isPresent()) {
            return new ResponseEntity<>("Tasklist name already exist", HttpStatus.BAD_REQUEST);
        }
        Tasklist newTasklist = new Tasklist(
                tasklistDto.getName().substring(0, 1).toUpperCase() + tasklistDto.getName().substring(1));
        newTasklist.setUser(user.get());
        tasklistRepository.save(newTasklist);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);

    }

    public List<Tasklist> getUserTasklists(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            return tasklistRepository.findTasklistByUserId(user_id);
        }
        throw new EntityNotFoundException("User not found");
    }

}
