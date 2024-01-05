package com.example.taskflow.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class CustomError implements Serializable {
    private final String field;
    private final String message;
}
