package com.example.taskflow.web.rest;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.mapper.TaskMapper;
import com.example.taskflow.service.TagService;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.Response;
import com.example.taskflow.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Response<List<TaskDto>>> findAll(){
        Response<List<TaskDto>> response = new Response<>();
        List<TaskDto> taskList = taskService.findAll().stream()
                .map(TaskMapper::toDto)
                .toList();
        response.setResult(taskList);
        if (taskList.isEmpty())
            response.setMessage("There are no tasks");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<TaskDto>> createTask(@RequestBody @Valid TaskDto taskDto) throws ValidationException {
        Response<TaskDto> response = new Response<>();
        Task task = TaskMapper.toEntity(taskDto);
        if (task.getAssignedTo() != null){
            User assignedToUser = userService.findByUsername(taskDto.getAssignedTo());
            task.setAssignedTo(assignedToUser);
        }

        Set<Tag> tags = taskDto.getTags().stream()
                .map(tagName -> tagService.findOrCreateTag(tagName))
                .collect(Collectors.toSet());

        task.setTags(tags);
        TaskDto savedTaskDto = TaskMapper.toDto(taskService.save(task));

        response.setResult(savedTaskDto);
        response.setMessage("Task created successfully");
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/assign/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<TaskDto>> assignTask(@PathVariable Long id, @PathVariable String username) throws ValidationException {
        Response<TaskDto> response = new Response<>();
        try {
            TaskDto updatedTaskDto = TaskMapper.toDto(taskService.assignTask(id, username));
            response.setResult(updatedTaskDto);
            response.setMessage("Task assigned successfully");
            return ResponseEntity.ok().body(response);
        } catch (AccessDeniedException ex) {
            response.setMessage("Access denied. Only admins are allowed to perform this operation.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<TaskDto>> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDto) throws ValidationException {
        Response<TaskDto> response = new Response<>();
        Task task = TaskMapper.toEntity(taskDto);
        if (task.getAssignedTo() != null){
            User assignedToUser = userService.findByUsername(taskDto.getAssignedTo());
            task.setAssignedTo(assignedToUser);
        }

        Set<Tag> tags = taskDto.getTags().stream()
                .map(tagName -> tagService.findOrCreateTag(tagName))
                .collect(Collectors.toSet());

        task.setId(id);
        task.setTags(tags);
        TaskDto updatedTaskDto = TaskMapper.toDto(taskService.update(task));

        response.setResult(updatedTaskDto);
        response.setMessage("Task updated successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<Response<List<TaskDto>>> findMyTasks() throws ValidationException {
        Response<List<TaskDto>> response = new Response<>();
        List<TaskDto> taskList = taskService.findMyTasks().stream()
                .map(TaskMapper::toDto)
                .toList();
        response.setResult(taskList);
        if (taskList.isEmpty())
            response.setMessage("You have no assigned tasks in this moment");
        return ResponseEntity.ok().body(response);
    }

    // remplace a task
    @PutMapping("/{id}/remplace")
    public ResponseEntity<Response<TaskDto>> remplaceTask(@PathVariable Long id) throws ValidationException {
        Response<TaskDto> response = new Response<>();
        TaskDto updatedTaskDto = TaskMapper.toDto(taskService.remplaceTask(id));
        response.setResult(updatedTaskDto);
        response.setMessage("Demanded task remplaced successfully");
        return ResponseEntity.ok().body(response);
    }
}
