package com.example.taskflow.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 25, message = "Title must be between 2 and 25 characters long")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotBlank(message = "Status is required")
    @Builder.Default
    private String status = "TODO";

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDateTime endDate;

    @NotNull(message = "Tags are required")
    private Set<TagDto> tags;
    private String createdBy;

    @NotBlank(message = "Assigned to is required")
    private String assignedTo;

    @Builder.Default
    private boolean remplaced = false;
}
