package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.model.auth.AuthenticationResponse;
import org.example.moviemate.model.auth.RegisterRequest;
import org.example.moviemate.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_ShouldReturnToken_WhenUserIsNew() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("yeniUser");
        request.setEmail("test@mail.com");
        request.setPassword("12345");

        when(userRepository.existsByUsername("yeniUser")).thenReturn(false);
        when(passwordEncoder.encode("12345")).thenReturn("encoded_pass");
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("mock_token");

        AuthenticationResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mock_token", response.getToken());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("movcudUser");

        when(userRepository.existsByUsername("movcudUser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(request));

        assertEquals("Bu istifadəçi adı artıq mövcuddur!", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}