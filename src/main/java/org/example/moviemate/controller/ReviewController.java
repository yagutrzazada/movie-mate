package org.example.moviemate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.moviemate.model.dto.ReviewDTO;
import org.example.moviemate.model.response.ApiResponse;
import org.example.moviemate.service.intf.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addReview(@RequestBody @Valid ReviewDTO reviewDTO, Principal principal) {
        reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(ApiResponse.success("Rəy uğurla əlavə edildi!"));
    }

    @GetMapping("/movie/{movieId}/me")
    public ResponseEntity<ApiResponse<ReviewDTO>> getMyReview(@PathVariable Long movieId, Principal principal) {
        ReviewDTO myReview = reviewService.getUserReviewForMovie(movieId, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(myReview, "Sizin rəyiniz"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id, Principal principal) {
        reviewService.deleteReview(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Rəy uğurla silindi!"));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByMovie(@PathVariable Long movieId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(ApiResponse.success(reviews, "Filmin rəyləri"));
    }
}