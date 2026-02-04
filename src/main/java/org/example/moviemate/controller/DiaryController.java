package org.example.moviemate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.DiaryDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<Void>> logMovie(
            @RequestParam Long movieId,
            Principal principal
    ) {
        diaryService.logMovie(movieId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Film gündəliyə uğurla əlavə edildi!"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiaryDTO>>> getUserDiary(
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        List<DiaryDTO> diary = diaryService.getUserDiary(userId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(diary, "İzləmə tarixçəniz"));
    }

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<ApiResponse<Void>> removeMovieFromDiary(
            @PathVariable Long movieId,
            @RequestParam(required = false) Long userId,
            Principal principal
    ) {
        diaryService.removeMovieFromDiary(userId, movieId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Film tarixçədən silindi!"));
    }
}