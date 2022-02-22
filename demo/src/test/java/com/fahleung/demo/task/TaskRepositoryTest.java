package com.fahleung.demo.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void testDeleteByNameAndTasklistId() {

    }

    @Test
    void testFindByNameAndTasklistName() {

    }

    @Test
    void testSwitchCompleteTaskByNameAndTasklistId() {

    }
}
