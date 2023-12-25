package com.example.taskflow.mapper;

import com.example.taskflow.domain.Task;
import com.example.taskflow.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);

    List<TaskDto> tasksToDTOs(List<Task> tasks);

}
