package com.example.taskflow.config;

import com.example.taskflow.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private UserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // pass the request to the next filter in the chain
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        // check if the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // load the user details from the database
            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
            // if token is valid configure Spring Security to manually set authentication
            if (jwtService.isTokenValid(jwt, userDetails)){
                // create object of UsernamePasswordAuthenticationToken, and pass the user details and the authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // enforce the authentication Token with the details from the request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // update SecurityContextHolder with the authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // send the request to the next filter in the chain
        filterChain.doFilter(request, response);

    }
}
