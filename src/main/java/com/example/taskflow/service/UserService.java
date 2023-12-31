package com.example.taskflow.service;

import com.example.taskflow.domain.User;
import com.example.taskflow.dto.UserDto;
import com.example.taskflow.utils.ValidationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService{
    List<User> findAll();
    User findById(Long id) throws ValidationException;
    User save(User user) throws ValidationException;
    User update(User user);
    void deleteById(Long id) throws ValidationException;

    User findByUsername(String assignedTo) throws ValidationException;

}
