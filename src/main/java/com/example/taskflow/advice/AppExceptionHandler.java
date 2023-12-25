package com.example.taskflow.advice;


import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import com.example.taskflow.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Response<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Response<Object> response = new Response<>();
        List<CustomError> errorList = new ArrayList<>();
        response.setMessage("Validation error");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errorList.add(new CustomError(fieldName, errorMessage));
        });
        response.setErrors(errorList);
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    private Response<Object> handleValidationExceptions(ValidationException ex) {
        Response<Object> response = new Response<>();
        List<CustomError> errorList = new ArrayList<>();
        errorList.add(ex.getCustomError());
        response.setMessage("Validation error");
        response.setErrors(errorList);
        return response;
    }
}
