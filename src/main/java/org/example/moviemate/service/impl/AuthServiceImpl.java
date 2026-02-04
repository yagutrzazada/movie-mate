package org.example.moviemate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.model.auth.AuthenticationResponse;
import org.example.moviemate.model.auth.LoginRequest;
import org.example.moviemate.model.auth.RegisterRequest;
import org.example.moviemate.model.enums.Role;
import org.example.moviemate.security.JwtService;
import org.example.moviemate.service.intf.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("ActionLog.register.start - username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("ActionLog.register.fail - Username tutulub: {}", request.getUsername());
            throw new RuntimeException("Bu istifadəçi adı artıq mövcuddur!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        user.setRole(Role.USER);

        userRepository.save(user);

        User securityUser = new User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );

        var jwtToken = jwtService.generateToken(securityUser);

        log.info("ActionLog.register.success - username: {}", request.getUsername());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        log.info("ActionLog.login.start - username: {}", request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        User securityUser = new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );

        var jwtToken = jwtService.generateToken(securityUser);

        log.info("ActionLog.login.success - username: {}", request.getUsername());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }}