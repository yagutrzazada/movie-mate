package org.example.moviemate.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.dao.repository.WatchlistRepository;
import org.example.moviemate.exception.NotFoundException;
import org.example.moviemate.mapper.WatchlistMapper;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.example.moviemate.model.enums.Role;
import org.example.moviemate.service.intf.WatchlistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final WatchlistMapper watchlistMapper;

    @Override
    @Transactional
    public void addMovieToWatchlist(Long targetUserId, Long movieId, String currentUsername) {
        Long finalUserId = determineTargetUserId(targetUserId, currentUsername);
        log.info("ActionLog.addMovieToWatchlist - Final User ID: {}, Movie ID: {}", finalUserId, movieId);

        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Film tapılmadı ID: " + movieId));

        UserEntity user = userRepository.findById(finalUserId)
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı"));

        WatchlistEntity watchlist = watchlistRepository.findByUserId(finalUserId)
                .orElseGet(() -> createEmptyWatchlist(user));

        if (watchlist.getMovies().contains(movie)) {
            throw new RuntimeException("Bu film artıq siyahıda mövcuddur!");
        }

        watchlist.getMovies().add(movie);
        watchlist.setAddedAt(LocalDateTime.now());

        watchlistRepository.save(watchlist);
    }

    @Override
    @Transactional
    public void removeMovieFromWatchlist(Long targetUserId, Long movieId, String currentUsername) {
        Long finalUserId = determineTargetUserId(targetUserId, currentUsername);

        WatchlistEntity watchlist = watchlistRepository.findByUserId(finalUserId)
                .orElseThrow(() -> new NotFoundException("Watchlist tapılmadı"));

        boolean removed = watchlist.getMovies().removeIf(movie -> movie.getId().equals(movieId));

        if (removed) {
            watchlist.setAddedAt(LocalDateTime.now());
            watchlistRepository.save(watchlist);
        }
    }

    @Override
    public WatchlistDTO getUserWatchlist(Long targetUserId, String currentUsername) {
        Long finalUserId = determineTargetUserId(targetUserId, currentUsername);
        return watchlistRepository.findByUserId(finalUserId)
                .map(watchlistMapper::toWatchlistDTO)
                .orElseGet(WatchlistDTO::new);
    }

    private Long determineTargetUserId(Long targetUserId, String currentUsername) {
        UserEntity currentUser = userRepository.findByUsername(currentUsername)
                .or(() -> userRepository.findByEmail(currentUsername))
                .orElseThrow(() -> new NotFoundException("Sistemdə daxil olan istifadəçi tapılmadı"));

        if (targetUserId != null && currentUser.getRole() == Role.ADMIN) {
            return targetUserId;
        }
        return currentUser.getId();
    }

    private WatchlistEntity createEmptyWatchlist(UserEntity user) {
        WatchlistEntity newWatchlist = new WatchlistEntity();
        newWatchlist.setUser(user);
        newWatchlist.setMovies(new ArrayList<>());
        newWatchlist.setAddedAt(LocalDateTime.now());
        return watchlistRepository.save(newWatchlist);
    }
}