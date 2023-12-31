package com.example.taskflow.web.rest;

import com.example.taskflow.dto.UserDto;
import com.example.taskflow.dto.responce.UserDtoResponse;
import com.example.taskflow.mapper.UserMapper;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.Response;
import com.example.taskflow.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response<List<UserDtoResponse>>> getAllUsers(){
        Response<List<UserDtoResponse>> response = new Response<>();
        List<UserDtoResponse> userList = userService.findAll().stream().map(UserMapper::toDto).toList();
        response.setResult(userList);
        if (userList.isEmpty())
            response.setMessage("There are no users");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<UserDtoResponse>> createUser(@Valid @RequestBody UserDto userDto) throws ValidationException {
        Response<UserDtoResponse> response = new Response<>();
        UserDtoResponse user = UserMapper.toDto(userService.save(UserMapper.toEntity(userDto)));
        response.setResult(user);
        response.setMessage("User created successfully");
        return ResponseEntity.ok().body(response);
    }

}
