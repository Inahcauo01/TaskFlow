package com.example.taskflow.service;

import com.example.taskflow.domain.Task;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    public List<Task> findAll();
    public Task findById(Long id) throws ValidationException;
    public Task save(Task task) throws ValidationException;
    public Task update(Task task);
    public void deleteById(Long id) throws ValidationException;
}
