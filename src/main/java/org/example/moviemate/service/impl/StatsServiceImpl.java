package org.example.moviemate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.repository.DiaryRepository;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.ReviewRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.model.dto.SystemStatsDTO;
import org.example.moviemate.model.dto.UserStatsDTO;
import org.example.moviemate.service.intf.StatsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public UserStatsDTO getUserStats(Long userId){
        log.info("ActionLog.getUserStats.start - userId: {}", userId);

        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        long watchedCount = diaryRepository.countMoviesByUserIdAndWatchedAtAfter(userId, oneYearAgo);
        long reviewCount = reviewRepository.countByUserIdAndCreatedAtAfter(userId, oneYearAgo);


        String favGenre = diaryRepository.findFavoriteGenreByUserId(userId)
                .orElse("Hələ yoxdur");

        log.info("ActionLog.getUserStats.success - User stats generated");

        return UserStatsDTO.builder()
                .userId(userId)
                .watchedMoviesLastYear(watchedCount)
                .reviewsLastYear(reviewCount)
                .favoriteGenre(favGenre)
                .build();}

    @Override
    public SystemStatsDTO getSystemStats() {
        log.info("ActionLog.getSystemStats.start");

        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        long totalUsers = userRepository.count();
        long totalMovies = movieRepository.count();

        //son 1 il
        long newUsers = userRepository.countByCreatedAtAfter(oneYearAgo);
        long newMovies = movieRepository.countByCreatedAtAfter(oneYearAgo);

        log.info("ActionLog.getSystemStats.success");

        return SystemStatsDTO.builder()
                .totalUsers(totalUsers)
                .totalMovies(totalMovies)
                .newUsersLastYear(newUsers)
                .newMoviesLastYear(newMovies)
                .build();
    }

}

