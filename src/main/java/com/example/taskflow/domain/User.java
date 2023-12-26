package com.example.taskflow.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "createdBy")
    private Set<Task> createdTasks;

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> assignedTasks;

    private boolean admin;
}
