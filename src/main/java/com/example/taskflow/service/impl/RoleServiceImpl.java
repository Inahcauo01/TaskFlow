package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Role;
import com.example.taskflow.repository.RoleRepository;
import com.example.taskflow.service.RoleService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        Role roleLoaded = roleRepository.findByAuthority(role.getAuthority()).orElse(null);
        if (roleLoaded != null)
            return roleLoaded;
        return roleRepository.save(role);
    }


    @Override
    public List<Role> saveAll(Collection<Role> roles) {
        return roleRepository.saveAll(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByAuthority(String authority) throws ValidationException {
        return roleRepository.findByAuthority(authority).orElseThrow(
                ()-> new ValidationException(new CustomError("role", "Role not found"))
        );
    }
}
