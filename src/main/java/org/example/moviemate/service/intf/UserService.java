package org.example.moviemate.service.intf;

import org.example.moviemate.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void validateUserAccess(Long targetUserId, String currentEmail);

    UserDTO getByUsername(String username);

}
