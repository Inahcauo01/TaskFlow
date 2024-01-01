package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Task save(Task task) throws ValidationException {
        validateTask(task);

        // get current user and set it as createdBy
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        task.setCreatedBy(currentUser);

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

    private void validateTask(Task task) throws ValidationException {
        // check if end date is after start date
        if (task.getEndDate().isBefore(task.getStartDate()))
            throw new ValidationException(new CustomError("endDate", "End date must be after start date"));

        // check if start date is before 3 days from now
        if (task.getStartDate().isBefore(task.getStartDate().minusDays(3)))
            throw new ValidationException(new CustomError("startDate", "Start date must be at least 3 days from now"));

        // check if task title is unique
        if (taskRepository.existsByTitle(task.getTitle()))
            throw new ValidationException(new CustomError("title", "Task with title " + task.getTitle() + " already exists"));
    }
}
