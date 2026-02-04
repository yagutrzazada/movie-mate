package org.example.moviemate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.WatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/add/{movieId}")
    public ResponseEntity<ApiResponse<Void>> addMovie(
            @PathVariable Long movieId,
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        watchlistService.addMovieToWatchlist(userId, movieId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(null, "Film siyahıya əlavə edildi!"));
    }

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<ApiResponse<Void>> removeMovie(
            @PathVariable Long movieId,
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        watchlistService.removeMovieFromWatchlist(userId, movieId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(null, "Film siyahıdan çıxarıldı!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<WatchlistDTO>> getWatchlist(
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        WatchlistDTO watchlist = watchlistService.getUserWatchlist(userId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(watchlist, "İzləmə siyahısı gətirildi"));
    }
}