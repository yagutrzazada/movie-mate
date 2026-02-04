package org.example.moviemate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.auth.AuthenticationResponse;
import org.example.moviemate.model.auth.LoginRequest;
import org.example.moviemate.model.auth.RegisterRequest;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response = authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Qeydiyyat uğurla tamamlandı!")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Giriş uğurla edildi!")
        );
    }
}