package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.domain.enums.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserService;
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
    private final UserService userService;


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
    public Task update(Task task) throws ValidationException {
        validateUpdateTask(task);

        if (task.getEndDate().isBefore(task.getEndDate()))
            task.setStatus(TaskStatus.valueOf("UNDONE"));

        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!taskRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "Task with id " + id + " not found"));
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignTask(Long id, String username) throws ValidationException {
        Task task = findById(id);
        User assignedToUser = userService.findByUsername(username);
        if (task.getAssignedTo() != null && task.getAssignedTo().equals(assignedToUser))
            throw new ValidationException(new CustomError("assigned to", "Task already assigned to this user"));
        task.setAssignedTo(assignedToUser);
        return update(task);
    }

    @Override
    public List<Task> findMyTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return taskRepository.findByAssignedTo(currentUser);
    }

    private void validateTask(Task task) throws ValidationException {
        // not allow to assign task to other user if not admin
        if (task.getAssignedTo() != null) {
            User assignedToUser = task.getAssignedTo();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            if (!assignedToUser.equals(currentUser) && !(currentUser).hasRole("ROLE_ADMIN"))
                throw new ValidationException(new CustomError("assigned to", "Not allowed to assign task to other user if not admin"));
        }

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

    private void validateUpdateTask(Task task) throws ValidationException {
        // not allow to change status after end date
        if (task.getStatus() != null && task.getEndDate().isBefore(task.getEndDate()))
            throw new ValidationException(new CustomError("Status", "Not allowed to change status after end date"));

        // not allow to change assignedTo if not admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (task.getAssignedTo() != null && !currentUser.hasRole("ROLE_ADMIN"))
            throw new ValidationException(new CustomError("assignedTo", "Not allowed to change assignedTo"));

        // not allow to change assignedTo if remplaced is true
        if (task.getAssignedTo() != null && task.isRemplaced())
            throw new ValidationException(new CustomError("assignedTo", "Not allowed to change assignedTo if remplaced is true"));

        // check if changed status is changed to DONE after end date
        if (task.getStatus().toString().equals("DONE") && task.getEndDate().isAfter(task.getEndDate()))
            throw new ValidationException(new CustomError("status", "Status cannot be changed to DONE after end date"));

        // check if changed status is changed to IN_PROGRESS before start date
        if (task.getStatus().toString().equals("IN_PROGRESS") && task.getStartDate().isBefore(task.getStartDate()))
            throw new ValidationException(new CustomError("status", "Status cannot be changed to IN_PROGRESS before start date"));


    }
}
