package com.example.taskflow.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<String> roles;
    private Integer jetons = 2;
}
