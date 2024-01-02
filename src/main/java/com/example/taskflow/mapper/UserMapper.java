package com.example.taskflow.mapper;

import com.example.taskflow.domain.Task;
import com.example.taskflow.domain.User;
import com.example.taskflow.dto.RoleDto;
import com.example.taskflow.dto.UserDto;
import com.example.taskflow.dto.responce.UserDtoResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserMapper {
    private UserMapper() {}

    public static UserDtoResponse toDto(User user){
        return UserDtoResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .jetons(user.getJetons())
                .build();
    }

    public static User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .enabled(userDto.isEnabled())
                .authorities(
                        Set.of(
                                RoleMapper.toEntity(
                                        RoleDto.builder()
                                                .authority("ROLE_USER")
                                                .build()
                                )
                        )
                )
                .jetons(userDto.getJetons())
                .build();
    }

}
