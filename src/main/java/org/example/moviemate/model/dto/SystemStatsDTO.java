package org.example.moviemate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatsDTO {
    private long totalUsers;
    private long totalMovies;

    private long newUsersLastYear;
    private long newMoviesLastYear;
}