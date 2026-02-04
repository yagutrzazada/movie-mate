package org.example.moviemate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.UserDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getMyProfile(Principal principal) {
        UserDTO user = userService.getByUsername(principal.getName());
        return ResponseEntity.ok(ApiResponse.success(user, "Xoş gəldiniz, bu sizin profilinizdir!"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> updateMyProfile(
            @Valid @RequestBody UserDTO userDTO,
            Principal principal
    ) {
        UserDTO currentUser = userService.getByUsername(principal.getName());

        UserDTO updatedUser = userService.updateUser(currentUser.getId(), userDTO);

        return ResponseEntity.ok(ApiResponse.success(updatedUser, "Profiliniz uğurla yeniləndi!"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(
            @PathVariable Long userId,
            Principal principal
    ) {
        userService.validateUserAccess(userId, principal.getName());
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success(user, "İstifadəçi məlumatları"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserDTO userDTO,
            Principal principal
    ) {
        userService.validateUserAccess(userId, principal.getName());
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "Profil uğurla yeniləndi!"));
    }
}