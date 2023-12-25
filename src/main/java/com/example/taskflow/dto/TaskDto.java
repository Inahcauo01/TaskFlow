package com.example.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    private Set<TagDto> tags;

    private UserDto createdBy;
    private UserDto assignedTo;
}
