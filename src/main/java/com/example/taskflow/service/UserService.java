package com.example.taskflow.service;

import com.example.taskflow.domain.User;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> findAll();
    public User findById(Long id) throws ValidationException;
    public User save(User user);
    public User update(User user);
    public void deleteById(Long id) throws ValidationException;
}
