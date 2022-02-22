package com.fahleung.demo.tasklist;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TasklistServiceTest {

    @Mock
    private TasklistRepository tasklistRepository;
    private TasklistService underTest;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new TasklistService(tasklistRepository, userRepository);
    }

    @Test
    void testGetUserTasklists() {
        Long id = (long) 1;
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";
        User user = new User(id, username, password,
                email);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Tasklist tasklist1 = new Tasklist("Tasklist1");
        Tasklist tasklist2 = new Tasklist("Tasklist2");
        Tasklist tasklist3 = new Tasklist("Tasklist3");
        when(tasklistRepository.findTasklistByUserId(id)).thenReturn(List.of(tasklist1, tasklist2, tasklist3));
        assertEquals(underTest.getUserTasklists(id), List.of(tasklist1, tasklist2, tasklist3));
    }

    @Test
    void willThrowIfTasklistsDontExist() {
        Long id = (long) 1;
        when(userRepository.findById(any())).thenThrow(new EntityNotFoundException("User not found"));
        assertThatThrownBy(() -> underTest.getUserTasklists(id)).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining((String.format("User not found")));
    }

    @Test
    void testSaveTasklist() {
        Long id = (long) 1;
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";
        User user = new User(id, username, password,
                email);
        Tasklist tasklist = new Tasklist("Tasklist1");
        TasklistDto tasklistDto = new TasklistDto();
        tasklistDto.setLogUsername("Hugo");
        tasklistDto.setUser_id(id);
        tasklistDto.setName(tasklist.getName());

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklistDto.getName(), tasklistDto.getUser_id()))
                .thenReturn(Optional.empty());

        assertTrue(underTest.saveTasklist(tasklistDto).getBody().equals("Saved"));
        assertTrue(underTest.saveTasklist(tasklistDto).getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    void testIfWrongUsernameInSaveTasklist() {
        Long id = (long) 1;
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";
        User user = new User(id, username, password,
                email);
        TasklistDto tasklistDto = new TasklistDto();
        tasklistDto.setLogUsername("Thomas");
        tasklistDto.setUser_id(id);
        tasklistDto.setName("Tasklist1");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        // assertFalse()
        assertTrue(underTest.saveTasklist(tasklistDto).getBody().equals("Access denied"));
        assertTrue(underTest.saveTasklist(tasklistDto).getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

    @Test
    void testIfTasklistAlreadyExistInSaveTasklist() {
        Long id = (long) 1;
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";
        User user = new User(id, username, password,
                email);
        Tasklist tasklist = new Tasklist("Tasklist1");
        TasklistDto tasklistDto = new TasklistDto();
        tasklistDto.setLogUsername("Hugo");
        tasklistDto.setUser_id(id);
        tasklistDto.setName(tasklist.getName());

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(tasklistRepository.findByNameAndUserId(tasklistDto.getName(), tasklistDto.getUser_id()))
                .thenReturn(Optional.of(tasklist));

        assertTrue(underTest.saveTasklist(tasklistDto).getBody().equals("Tasklist name already exist"));
        assertTrue(underTest.saveTasklist(tasklistDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testIfUserNotFoundInSaveTasklist() {
        Long id = (long) 1;
        TasklistDto tasklistDto = new TasklistDto();
        tasklistDto.setLogUsername("Thomas");
        tasklistDto.setUser_id(id);
        tasklistDto.setName("Tasklist1");
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertTrue(underTest.saveTasklist(tasklistDto).getBody().equals("Problem occured"));
        assertTrue(underTest.saveTasklist(tasklistDto).getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
}
