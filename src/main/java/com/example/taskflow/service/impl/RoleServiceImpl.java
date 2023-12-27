package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Role;
import com.example.taskflow.repository.RoleRepository;
import com.example.taskflow.service.RoleService;
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
        Role roleLoaded = roleRepository.findByAuthority(role.getAuthority());
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
    public Role findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }
}
