package com.fahleung.demo.tasklist;

import java.util.List;
import java.util.Optional;

import com.fahleung.demo.user.User;
import com.fahleung.demo.user.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TasklistRepositoryTest {

    @Autowired
    private TasklistRepository underTest;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Tasklist tasklist1;
    private Tasklist tasklist2;
    private Tasklist tasklist3;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @BeforeEach
    void setUp() {
        String username = "Hugo";
        String email = "hugo@gmail.com";
        String password = "azerty123";

        // given
        User user = new User(username, password, password,
                email);
        this.user = userRepository.save(user);

        this.tasklist1 = new Tasklist("Tasklist1");
        tasklist1.setUser(user);
        this.tasklist2 = new Tasklist("Tasklist2");
        tasklist2.setUser(user);
        this.tasklist3 = new Tasklist("Tasklist3");
        tasklist3.setUser(user);
        underTest.saveAll(List.of(tasklist1, tasklist2, tasklist3));
    }

    @Test
    void testFindAllTasklistByUsername() {
        // when
        List<Tasklist> tasklists = underTest.findAllTasklistByUsername(user.getUsername());
        // then
        assertThat(tasklists).containsAll(List.of(tasklist1, tasklist2, tasklist3));
    }

    @Test
    void testFindByNameAndUserId() {
        // when
        Optional<Tasklist> tasklist = underTest.findByNameAndUserId(tasklist1.getName(), user.getId());
        // then
        assertThat(tasklist.isPresent());
    }

    @Test
    void testFindTasklistByUserId() {
        // when
        List<Tasklist> tasklists = underTest.findTasklistByUserId(user.getId());
        // then
        assertThat(tasklists).containsAll(List.of(tasklist1, tasklist2, tasklist3));
    }
}
