package com.fahleung.demo.task;

import java.util.List;
import java.util.Optional;

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

    public List<Tasklist> getTasklists(Long user_id) {
        return tasklistRepository.findTasklistByUserId(user_id);
    }

    public ResponseEntity<String> saveTasklist(Long user_id, String name, String username) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Tasklist> tasklist = tasklistRepository.findByNameAndUserId(name, user_id);
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(username)) {
                return new ResponseEntity<>("Problem occured", HttpStatus.FORBIDDEN);
            }
            // check if tasklist exist
            if (tasklist.isPresent()) {
                return new ResponseEntity<>("Tasklist name already exist", HttpStatus.BAD_REQUEST);
            }
            Tasklist newTasklist = new Tasklist(name.substring(0, 1).toUpperCase() + name.substring(1));
            newTasklist.setUser(user.get());
            tasklistRepository.save(newTasklist);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public List<Tasklist> getUserTasklists(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            return tasklistRepository.findTasklistByUserId(user_id);
        }
        return null;
    }

}
