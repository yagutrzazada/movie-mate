package org.example.moviemate.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiaryDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private List<String> movies;

    @NotNull(message = "Watched time cannot be null")
    private LocalDateTime watchedAt;
}