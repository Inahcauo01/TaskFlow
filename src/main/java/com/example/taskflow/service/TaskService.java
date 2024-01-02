package com.example.taskflow.service;

import com.example.taskflow.domain.Task;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<Task> findAll();
    Task findById(Long id) throws ValidationException;
    Task save(Task task) throws ValidationException;
    Task update(Task task) throws ValidationException;
    void deleteById(Long id) throws ValidationException;

    Task assignTask(Long id, String username) throws ValidationException;
}
