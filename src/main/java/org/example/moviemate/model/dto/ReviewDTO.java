package org.example.moviemate.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    @Schema(hidden = true)
    private Long userId;

    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    @Positive(message = "Rating must be a positive number")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "The rating should be at most 10")
    private Double rating;

    @Size(max = 500, message = "Comment must be at most 500 characters")
    private String comment;
}

