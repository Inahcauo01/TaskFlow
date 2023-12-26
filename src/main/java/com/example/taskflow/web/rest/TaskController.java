package com.example.taskflow.web.rest;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.mapper.TaskMapper;
import com.example.taskflow.service.TagService;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.Response;
import com.example.taskflow.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        User createdByUser = userService.findByUsername(taskDto.getCreatedBy());
        User assignedToUser = userService.findByUsername(taskDto.getAssignedTo());

        Set<Tag> tags = taskDto.getTags().stream()
                .map(tagName -> tagService.findOrCreateTag(tagName))
                .collect(Collectors.toSet());

        task.setCreatedBy(createdByUser);
        task.setAssignedTo(assignedToUser);
        task.setTags(tags);
        TaskDto savedTaskDto = TaskMapper.toDto(taskService.save(task));

        response.setResult(savedTaskDto);
        response.setMessage("Task created successfully");
        return ResponseEntity.ok().body(response);
    }

}
