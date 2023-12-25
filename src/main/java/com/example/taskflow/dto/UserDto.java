package com.example.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @UniqueElements(message = "Username already exists")
    private String username;
    private String firstName;
    private String lastName;
    @UniqueElements(message = "Email already exists")
    private String email;

    @Builder.Default
    private boolean isAdmin=false;
}
