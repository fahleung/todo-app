package com.fahleung.demo.tasklist;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TasklistControllerTest {

    @Mock
    private TasklistService tasklistService;
    private TasklistController underTest;

    @BeforeEach
    void setUp() {
        underTest = new TasklistController(tasklistService);
    }

    @Test
    void testGetTasklists() {
        Long id = (long) 1;
        Tasklist tasklist1 = new Tasklist();
        Tasklist tasklist2 = new Tasklist();
        List<Tasklist> list = List.of(tasklist1, tasklist2);
        when(tasklistService.getUserTasklists(id)).thenReturn(list);
        assertTrue(list.equals(underTest.getTasklists(id)));
    }

    @Test
    void testGetTasklistsError() {
        Long id = (long) 1;
        EntityNotFoundException exception = new EntityNotFoundException("User not found");
        when(tasklistService.getUserTasklists(id)).thenThrow(exception);
        assertThatThrownBy(() -> underTest.getTasklists(id)).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining((String.format("User not found")));
    }

    @Test
    void testSaveTasklist() {

    }
}
