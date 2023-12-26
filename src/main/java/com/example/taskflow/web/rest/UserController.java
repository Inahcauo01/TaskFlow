package com.example.taskflow.web.rest;

import com.example.taskflow.dto.UserDto;
import com.example.taskflow.mapper.UserMapper;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAllUsers(){
        Response<List<UserDto>> response = new Response<>();
        List<UserDto> userList = UserMapper.INSTANCE.usersToDTOs(userService.findAll());
        response.setResult(userList);
        if (userList.isEmpty())
            response.setMessage("There are no users");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<UserDto>> createUser(UserDto userDto){
        Response<UserDto> response = new Response<>();
        UserDto user = UserMapper.INSTANCE.toDto(userService.save(UserMapper.INSTANCE.toEntity(userDto)));
        response.setResult(user);
        response.setMessage("User created successfully");
        return ResponseEntity.ok().body(response);
    }
}
