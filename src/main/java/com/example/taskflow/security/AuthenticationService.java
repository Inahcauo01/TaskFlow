package com.example.taskflow.security;

import com.example.taskflow.auth.AuthenticationResponse;
import com.example.taskflow.auth.LoginRequest;
import com.example.taskflow.auth.RegisterRequest;
import com.example.taskflow.domain.Role;
import com.example.taskflow.domain.User;
import com.example.taskflow.repository.RoleRepository;
import com.example.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        // create a user out of the request
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
//                .authorities(getAuthorities(request.getRoles()))
                .build();

        // Set the saved roles to the user
        user.setAuthorities(getOrCreateRoles(request.getRoles()));


        userRepository.save(user);
        // generate & return a token for the user
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        // authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // find the user by username
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // generate & return token for the user
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    private Set<Role> getOrCreateRoles(Set<String> roleNames) {
        return roleNames.stream()
                .map(this::getOrCreateRole)
                .collect(Collectors.toSet());
    }

    private Role getOrCreateRole(String roleName) {
        // Check if the role exists
        return roleRepository.findByAuthority(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().authority(roleName).build()));
    }
}
