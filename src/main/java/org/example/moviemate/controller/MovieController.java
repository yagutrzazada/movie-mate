package org.example.moviemate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.MovieDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.model.validation.OnCreate;
import org.example.moviemate.service.intf.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<MovieDTO>> createMovie(
            @Validated(OnCreate.class) @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(ApiResponse.success(movieService.createMovie(movieDTO), "Film yaradıldı!"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<MovieDTO>> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(ApiResponse.success(movieService.updateMovie(id, movieDTO), "Film yeniləndi!"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(ApiResponse.success("Film silindi!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieDTO>>> getAllMovies() {
        return ResponseEntity.ok(ApiResponse.success(movieService.getAllMovies(), "Filmlər siyahıya alındı!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDTO>> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMovieById(id), "Film tapıldı"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MovieDTO>>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(ApiResponse.success(movieService.searchMovies(title), "Nəticələr siyahıya alındı"));
    }
}