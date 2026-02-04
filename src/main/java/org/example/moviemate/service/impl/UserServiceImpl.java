package org.example.moviemate.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.exception.NotFoundException;
import org.example.moviemate.mapper.UserMapper;
import org.example.moviemate.model.dto.UserDTO;
import org.example.moviemate.model.enums.Role;
import org.example.moviemate.service.intf.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long id) {
        log.info("ActionLog.getUserById.start - id: {}", id);

        UserEntity user = userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı: " + id));

        log.info("ActionLog.getUserById.success - id: {}", id);
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        log.info("ActionLog.updateUser.start - id: {}", userId);

        UserEntity existingUser = userRepository.findById(userId).
                orElseThrow(() -> {
                    log.error("ActionLog.updateUser.error - İstifadəçi tapılmadı id: {}", userId);
                    return new NotFoundException("İstifadəçi tapılmadı id: " + userId);
                });

        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
            if (!userDTO.getUsername().equals(existingUser.getUsername())) {
                if (userRepository.existsByUsername(userDTO.getUsername())) {
                    log.warn("ActionLog.updateUser.fail - Username artıq istifadədədir: {}", userDTO.getUsername());
                    throw new RuntimeException("Bu istifadəçi adı artıq mövcuddur!");
                }
                existingUser.setUsername(userDTO.getUsername());
            }
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            if (!userDTO.getEmail().equals(existingUser.getEmail())) {
                if (userRepository.existsByEmail(userDTO.getEmail())) {
                    log.warn("ActionLog.updateUser.fail - Email artıq istifadədədir: {}", userDTO.getEmail());
                    throw new RuntimeException("Bu email artıq mövcuddur!");
                }
                existingUser.setEmail(userDTO.getEmail());

            }
        }
        UserEntity savedUser = userRepository.save(existingUser);
        log.info("ActionLog.updateUser.success - User yeniləndi ID: {}", userId);

        return userMapper.toUserDTO(savedUser);
    }
    public void validateUserAccess(Long targetUserId, String currentUsername) {
        UserEntity currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("Sistemdə daxil olan istifadəçi tapılmadı"));

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isOwner = currentUser.getId().equals(targetUserId);

        if (!isAdmin && !isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bu əməliyyat üçün icazəniz yoxdur!");
        }
    }

    public UserDTO getByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı"));
        return userMapper.toUserDTO(user);
    }
}
