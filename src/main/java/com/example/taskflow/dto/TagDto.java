package com.example.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {
    private Long id;

    @NotNull(message = "Tag name cannot be null")
    @NotBlank(message = "Tag name cannot be blank")
    @UniqueElements(message = "Tag name already exists")
    private String name;
}
