package com.example.taskflow.service.impl;

import com.example.taskflow.domain.User;
import com.example.taskflow.repository.UserRepository;
import com.example.taskflow.service.RoleService;
import com.example.taskflow.service.UserService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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
    public User save(User user) throws ValidationException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty())
            user.setAuthorities(Set.of(roleService.findByAuthority("ROLE_USER"))); //default role
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

    @Override
    public User findByUsername(String assignedTo) throws ValidationException {
        return userRepository.findByUsername(assignedTo).orElseThrow(
                () -> new ValidationException(new CustomError("username", "User not found"))
        );
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

}
