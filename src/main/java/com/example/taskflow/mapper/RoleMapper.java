package com.example.taskflow.mapper;

import com.example.taskflow.domain.Role;
import com.example.taskflow.dto.RoleDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleMapper {
    private RoleMapper() {
    }

    public static RoleDto toDto(String role) {
        return RoleDto.builder()
                .authority(role)
                .build();
    }

    public static Role toEntity(RoleDto roleDto) {
        return Role.builder()
                .id(Role.builder()
                        .authority(roleDto.getAuthority())
                        .build()
                        .getId()
                )
                .authority(roleDto.getAuthority())
                .build();
    }
}
