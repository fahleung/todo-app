package com.fahleung.demo.task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        if (user.isPresent()) {
            // check with current logged user
            if (!user.get().getUsername().equals(username)) {
                return new ResponseEntity<>("Problem occured", HttpStatus.FORBIDDEN);
            }
            Tasklist tasklist = new Tasklist(name);
            tasklist.setUser(user.get());
            tasklistRepository.save(tasklist);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Problem occured", HttpStatus.BAD_REQUEST);
    }

    public List<Tasklist> getUserTasklists(String name) {
        Optional<User> user = userRepository.findUserByName(name);
        if (user.isPresent()) {
            return tasklistRepository.findTasklistByUsername(name);
        }
        return null;
    }

}
