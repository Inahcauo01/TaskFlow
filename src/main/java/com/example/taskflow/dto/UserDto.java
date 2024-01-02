package com.example.taskflow.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean enabled = true;

    private Set<String> roles;

    private Set<String> createdTasks;

    private Set<String> assignedTasks;

    private Integer jetons = 2;

}
