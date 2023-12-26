package com.example.taskflow.mapper;

import com.example.taskflow.domain.User;
import com.example.taskflow.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMapper {
    private UserMapper() {}

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .admin(user.isAdmin())
                .build();
    }

    public static User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .admin(userDto.isAdmin())
                .build();
    }
}
