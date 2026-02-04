package org.example.moviemate.service.intf;


import org.example.moviemate.model.auth.AuthenticationResponse;
import org.example.moviemate.model.auth.LoginRequest;
import org.example.moviemate.model.auth.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}