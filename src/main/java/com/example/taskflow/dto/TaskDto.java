package com.example.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Set<TagDto> tags;

    private String createdBy;
    private String assignedTo;
}
