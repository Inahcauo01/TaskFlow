package com.example.taskflow.mapper;

import com.example.taskflow.domain.User;
import com.example.taskflow.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);
    UserDto toDto(User user);

    List<UserDto> usersToDTOs(List<User> users);
}
