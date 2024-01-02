package com.example.taskflow.mapper;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.domain.enums.TaskStatus;
import com.example.taskflow.dto.TaskDto;
import lombok.Builder;

import java.util.stream.Collectors;

@Builder
public class TaskMapper {
    private TaskMapper() {}

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .tags(task.getTags().stream().map(TagMapper::toDto).collect(Collectors.toSet()))
                .createdBy(task.getCreatedBy().getUsername())
                .assignedTo((task.getAssignedTo() != null) ? task.getAssignedTo().getUsername() : null)
                .build();
    }

    public static Task toEntity(TaskDto taskDto) {
        User assignedToUser = (taskDto.getAssignedTo() != null) ?
                User.builder().username(taskDto.getAssignedTo()).build() :
                null;

        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(TaskStatus.valueOf(taskDto.getStatus()))
                .startDate(taskDto.getStartDate())
                .endDate(taskDto.getEndDate())
                .tags(taskDto.getTags().stream().map(TagMapper::toEntity).collect(Collectors.toSet()))
                .createdBy(User.builder()
                            .username(taskDto.getCreatedBy())
                            .build())
                .assignedTo(assignedToUser)
                .build();
    }
}
