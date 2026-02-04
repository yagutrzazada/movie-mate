package org.example.moviemate.service.impl;

import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.mapper.UserMapper;
import org.example.moviemate.model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updateUser_ShouldThrowException_WhenUsernameAlreadyExists() {
        Long userId = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setUsername("oldUser");

        UserDTO updateDTO = new UserDTO();
        updateDTO.setUsername("takenUser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername("takenUser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUser(userId, updateDTO));

        assertEquals("Bu istifadəçi adı artıq mövcuddur!", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserById_ShouldReturnUserDTO_WhenUserExists() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("testUser");

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setUsername("testUser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(expectedDTO);

        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }
}