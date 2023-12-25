package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Task;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) throws ValidationException {
        return taskRepository.findById(id).orElseThrow(
                () -> new ValidationException(new CustomError("id", "Task with id " + id + " not found"))
        );
    }

    @Override
    public Task save(Task task) {
        // TODO: validate task
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        // TODO: validate task
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!taskRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "Task with id " + id + " not found"));
        taskRepository.deleteById(id);
    }
}
