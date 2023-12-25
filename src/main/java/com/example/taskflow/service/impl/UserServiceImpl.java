package com.example.taskflow.service.impl;

import com.example.taskflow.domain.User;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) throws ValidationException {
        return userRepository.findById(id).orElseThrow(
                    () -> new ValidationException(new CustomError("id", "User not found")));
    }

    @Override
    public User save(User user) {
        // TODO: validation
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        // TODO: validation
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!userRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "User not found"));
        userRepository.deleteById(id);
    }
}
