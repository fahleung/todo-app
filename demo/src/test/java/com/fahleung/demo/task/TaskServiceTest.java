package com.fahleung.demo.task;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Optional;

import com.fahleung.demo.tasklist.Tasklist;
import com.fahleung.demo.tasklist.TasklistRepository;
import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TasklistRepository tasklistRepository;
    @Mock
    private UserRepository userRepository;

    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(taskRepository, tasklistRepository, userRepository);
    }

    @Test
    void testSaveTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));

        assertTrue(underTest.saveTask(taskDto).getBody().equals("Saved"));
        assertTrue(underTest.saveTask(taskDto).getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    void testIfUserNotFoundInSaveTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));

        assertTrue(underTest.saveTask(taskDto).getBody().equals("Problem occured"));
        assertTrue(underTest.saveTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfWrongUsernameInSaveTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Hugo");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));

        assertTrue(underTest.saveTask(taskDto).getBody().equals("Access denied"));
        assertTrue(underTest.saveTask(taskDto).getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

    @Test
    void testIfTasklistDoNotExistInSaveTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.empty());

        assertTrue(underTest.saveTask(taskDto).getBody().equals("Tasklist doesn't exist"));
        assertTrue(underTest.saveTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testDeleteTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.deleteTask(taskDto).getBody().equals("Deleted"));
        assertTrue(underTest.deleteTask(taskDto).getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void testIfUserNotFoundinDeleteTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.deleteTask(taskDto).getBody().equals("Problem occured"));
        assertTrue(underTest.deleteTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfWrongUsernameInDeleteTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Hugo");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.deleteTask(taskDto).getBody().equals("Access denied"));
        assertTrue(underTest.deleteTask(taskDto).getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

    @Test
    void testIfTasklistDoNotEXistInDeleteTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.empty());
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.deleteTask(taskDto).getBody().equals("Tasklist doesn't exist"));
        assertTrue(underTest.deleteTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfTaskDoNotExistInDeleteTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.empty());

        assertTrue(underTest.deleteTask(taskDto).getBody().equals("Task doesn't exist"));
        assertTrue(underTest.deleteTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testUpdateTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.updateTask(taskDto).getBody().equals("Completed"));
        assertTrue(underTest.updateTask(taskDto).getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void testIfUserNotFoundinUpdateTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.updateTask(taskDto).getBody().equals("Problem occured"));
        assertTrue(underTest.updateTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfWrongUsernameInUpdateTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Hugo");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.updateTask(taskDto).getBody().equals("Access denied"));
        assertTrue(underTest.updateTask(taskDto).getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

    @Test
    void testIfTasklistDoNotEXistInUpdateTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.empty());
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.of(task));

        assertTrue(underTest.updateTask(taskDto).getBody().equals("Tasklist doesn't exist"));
        assertTrue(underTest.updateTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfTaskDoNotExistInUpdateTask() {
        Long id = (long) 1;
        User user = new User(id, "Fabien", "azerty123",
                "fabien@gmail.com");
        Tasklist tasklist = new Tasklist("Tasklist1");
        TaskDto taskDto = new TaskDto();
        taskDto.setUser_id(id);
        taskDto.setTaskname("Task1");
        taskDto.setTasklistname("Tasklist1");
        taskDto.setLogUsername("Fabien");
        Task task = new Task("Task1", new Timestamp(System.currentTimeMillis()), false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklist.getName(), id)).thenReturn(Optional.of(tasklist));
        when(taskRepository.findByNameAndTasklistName(taskDto.getTaskname(), taskDto.getTasklistname()))
                .thenReturn(Optional.empty());

        assertTrue(underTest.updateTask(taskDto).getBody().equals("Task doesn't exist"));
        assertTrue(underTest.updateTask(taskDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

}
