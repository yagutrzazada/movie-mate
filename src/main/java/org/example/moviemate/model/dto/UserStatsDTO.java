package org.example.moviemate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
        private Long userId;
        private long watchedMoviesLastYear;
        private long reviewsLastYear;
        private String favoriteGenre;
    }

