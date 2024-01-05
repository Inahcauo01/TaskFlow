package com.example.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    @Builder.Default
    private String status = "PENDING";

    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotNull(message = "Task id is required")
    private Long taskId;

    @NotBlank(message = "Requested by id is required")
    private String requestedBy;

    private String assignedBy;
}
