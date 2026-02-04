package org.example.moviemate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.SystemStatsDTO;
import org.example.moviemate.model.dto.UserStatsDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.StatsService;
import org.example.moviemate.service.intf.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserStatsDTO>> getUserStats(
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        Long authenticatedId = userService.getByUsername(principal.getName()).getId();

        UserStatsDTO stats = statsService.getUserStats(userId != null ? userId : authenticatedId);

        return ResponseEntity.ok(ApiResponse.success(stats, "İstifadəçi statistikası"));
    }

    @GetMapping("/system")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<SystemStatsDTO>> getSystemStats() {
        return ResponseEntity.ok(ApiResponse.success(statsService.getSystemStats(), "Sistem statistikası"));
    }
}
