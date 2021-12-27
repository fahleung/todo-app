package com.fahleung.demo.task;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Task t WHERE t.name=:name AND t.tasklist.tasklist_id=:id")
    void deleteByNameAndTasklistId(@Param("name") String taskname, @Param("id") Long tasklist_id);

    @Query("SELECT t FROM Task t WHERE t.name=:taskname AND t.tasklist.name=:tasklistname")
    Optional<Task> findByNameAndTasklistName(@Param("taskname") String taskname,
            @Param("tasklistname") String tasklistname);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Task t SET t.completed=:isCompleted WHERE t.name=:name AND t.tasklist.tasklist_id=:id")
    void switchCompleteTaskByNameAndTasklistId(@Param("name") String taskname, @Param("id") Long tasklist_id,
            @Param("isCompleted") boolean isCompleted);

}
