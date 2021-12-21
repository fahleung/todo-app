package com.fahleung.demo.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TasklistRepository extends JpaRepository<Tasklist, Long> {

    @Query("SELECT tl FROM Tasklist tl WHERE tl.user.user_id=:id")
    List<Tasklist> findTasklistByUserId(@Param("id") Long user_id);

    @Query("SELECT tl FROM Tasklist tl WHERE tl.user.username=:name ORDER BY tl.tasklist_id DESC")
    List<Tasklist> findTasklistByUsername(@Param("name") String username);
}
