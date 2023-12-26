package com.example.taskflow.web.rest;

import com.example.taskflow.dto.TaskDto;
import com.example.taskflow.mapper.TaskMapper;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

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

}
