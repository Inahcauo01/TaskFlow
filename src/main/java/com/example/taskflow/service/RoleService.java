package com.example.taskflow.service;

import com.example.taskflow.domain.Role;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface RoleService {
    Role save(Role role);
    List<Role> findAll();
    List<Role> saveAll(Collection<Role> roles);
    Role findByAuthority(String authority);
}
