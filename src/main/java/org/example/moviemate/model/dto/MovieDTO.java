package org.example.moviemate.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.moviemate.model.enums.Genre;
import org.example.moviemate.model.validation.OnCreate;

import java.time.LocalDate;

@Data
public class MovieDTO {
    private Long id;

    @NotBlank(groups = OnCreate.class, message = "Title cannot be empty")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotNull(groups = OnCreate.class, message = "Genre cannot be null")
    private Genre genre;

    @NotNull(groups = OnCreate.class, message = "Release date cannot be null")
    private LocalDate releaseDate;

    @Size(max = 500, message = "Description length is too long")
    private String description;

    @PositiveOrZero(message = "Rating must be zero or positive")
    private double averageRating;

    private String director;
    private String actors;
}