package com.example.taskflow.repository;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitle(String title);

    List<Task> findByAssignedTo(User currentUser);
}
