package com.example.taskflow.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    private String firstName;
    private String lastName;
    private String username;
    private boolean enabled = true;

    private Set<String> roles;

    private Set<String> createdTasks;

    private Set<String> assignedTasks;
}
