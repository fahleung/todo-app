package com.fahleung.demo.tasklist;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasklist")
public class TasklistController {

    private final TasklistService tasklistService;

    @Autowired
    public TasklistController(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path = "{user_id}")
    public List<Tasklist> getTasklists(@PathVariable Long user_id) {
        return tasklistService.getUserTasklists(user_id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "{user_id}")
    public ResponseEntity<String> saveTasklist(@PathVariable Long user_id, @RequestBody Map<String, String> body,
            Principal principal) {
        TasklistDto tasklistDto = new TasklistDto();
        tasklistDto.setLogUsername(principal.getName());
        tasklistDto.setUser_id(user_id);
        tasklistDto.setName(body.get("name"));
        return tasklistService.saveTasklist(tasklistDto);
    }
}
